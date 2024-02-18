package com.example.ecoms.common.dao

import android.content.res.AssetManager
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.coroutineScope
import java.io.BufferedReader

/**
 * @overview
 *  A generic paginated object loader from file.
 *
 * @requires filePath is a valid path (in the src/main/assets directory) to a CSV file containing Customer data
 */
open class PagingFileSource<T: Any> (
  private val assetMan: AssetManager,
  private val filePath: String,
  private val objectFileProc: ObjectFileProc<T>
) : PagingSource<Int, T>() {

  private lateinit var dataSourceListener: (count: Int) -> Unit

  private lateinit var reader: BufferedReader

  private var objCount: Int = 0;

  private val LTAG = PagingFileSource::class.simpleName

  /**
   * @effects
   *  incrementally read objects from the file specified in #filePath, using #objectFileProc to
   *  convert each line to object and return a LoadResult.
   *
   *  The objects are read one page at a time, with the page size is specified in #params.loadSize.
   */
  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
    return try {
      val pageNumber = params.key ?: 0
      val pageSize = params.loadSize
      val readAheadLimit = 1024

      Log.d(LTAG, "loading page ${pageNumber} (pageSize: ${pageSize})...")
      // load objects for the current page
      if (!::reader.isInitialized) {
        // Open the CSV file
        val inputStream = assetMan.open(filePath)
        reader = inputStream.bufferedReader()
        // Skip the header row
        reader.readLine()
      }

      /* seems not needed:
      if (pageNumber > 0) {
        reader.reset()
      }
      */

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

      val page : LoadResult.Page<Int, T> =
       LoadResult.Page(
        data = objects,
        prevKey = prevKey,
        nextKey = nextKey
      )

      coroutineScope {  // inform listeners
        dataSourceListener(objCount)
      }

      return page

    } catch (e: Exception) {
      LoadResult.Error(e)
    }
  }

  override fun getRefreshKey(state: PagingState<Int, T>): Int? {
    return null // Return null if data cannot be refreshed based on a position
  }

  fun registerDataSourceListener(dataSourceListener: (count: Int) -> Unit) {
    this.dataSourceListener = dataSourceListener
  }
}