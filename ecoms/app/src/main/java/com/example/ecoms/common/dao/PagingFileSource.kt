package com.example.ecoms.common.dao

import android.content.res.AssetManager
import android.util.Log
import androidx.paging.PagingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStream

/**
 * @overview
 *  A generic paginated object loader from file.
 *  Assumption: file content is static (i.e. no new data are added to file while reading).
 *
 *  For dynamic data file, use [PagingDynamicFileSource] instead.
 *
 * @requires filePath is a valid path (in the src/main/assets directory) to a CSV file containing Customer data
 */
open class PagingFileSource<T: Any> (
  protected val objectFileProc: ObjectFileProc<T>
) : DataSource<T>() {

  protected lateinit var dataSourceListener: (count: Int) -> Unit

  protected lateinit var fileIs: InputStream

  protected lateinit var reader: BufferedReader

  protected var objCount: Int = 0;

  protected val LTAG = PagingFileSource::class.simpleName

  constructor(fileIs: InputStream, objectFileProc: ObjectFileProc<T>): this(objectFileProc) {
    this.fileIs = fileIs
  }

  constructor(assetMan: AssetManager, filePath: String, objectFileProc: ObjectFileProc<T>):
      this(objectFileProc) {
    this.fileIs = assetMan.open(filePath)
  }

  /**
   * @effects
   *  if [reader] is not initialised
   *    initialise it from [fileIs] and read the first line
   *  else
   *    do nothing
   */
  protected fun initReader() {
    if (!::reader.isInitialized) {
      // Open the CSV file
      reader = fileIs.bufferedReader()
      // Skip the header row
      reader.readLine()
    }
  }

  /**
   * @effects
   *  incrementally read objects from the file specified in #filePath, using #objectFileProc to
   *  convert each line to object and return a LoadResult.
   *
   *  The objects are read one page at a time, with the page size is specified in #params.loadSize.
   */
  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
    return withContext(Dispatchers.IO) {
      // performs on a IO thread to avoid blocking main thread while waiting for new lines
      try {
        val pageNumber = params.key ?: 0
        val pageSize = params.loadSize
        val readAheadLimit = 1024

        Log.d(LTAG, "loading page ${pageNumber} (pageSize: ${pageSize})...")
        // load objects for the current page
        initReader()

        // Read lines and parse
        val objects = mutableListOf<T>()
        var line: String? = null
        var readCount = 0
        // Important: readCount condition must appear first, to avoid skipping the next line after each page

        while (readCount < pageSize && (reader.readLine().also { line = it } != null)) {
          val obj: T = objectFileProc.toObject(line!!)

          Log.d(LTAG, "...loaded ${obj}")

          objects.add(obj)
          objCount++
          readCount++
        }

        reader.mark(readAheadLimit)

        // setup prev, next keys
        val prevKey = if (pageNumber > 0) pageNumber - 1 else null
        val nextKey = if (objects.isNotEmpty()) pageNumber + 1 else null

        Log.d(LTAG, "...read count (${readCount}); previous: ${prevKey}; next: ${nextKey}...")

        withContext(Dispatchers.Default) {  // inform listeners
          dataSourceListener(objCount)
        }

        // return the data page
        LoadResult.Page(
          data = objects,
          prevKey = prevKey,
          nextKey = nextKey
        )
      } catch (e: Exception) {
        LoadResult.Error(e)
      }
    }
  }

  override fun getRefreshKey(state: PagingState<Int, T>): Int? {
    //return null // Return null if data cannot be refreshed based on a position
    return state.anchorPosition
  }

  override fun registerDataSourceListener(dataSourceListener: (count: Int) -> Unit) {
    this.dataSourceListener = dataSourceListener
  }

  fun close() {
    if (::reader.isInitialized) reader.close()
  }
}