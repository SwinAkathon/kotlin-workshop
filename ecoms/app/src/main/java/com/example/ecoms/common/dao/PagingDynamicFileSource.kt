package com.example.ecoms.common.dao

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream

/**
 * @overview
 * A subtype of [PagingFileSource] to support dynamic data file (i.e. data are added to file
 * while reading).
 *
 * This is useful for processing streaming data.
 */
class PagingDynamicFileSource<T: Any>(fileIs: InputStream, objectFileProc: ObjectFileProc<T>)
  : PagingFileSource<T>(fileIs, objectFileProc){
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

        // use this loop to support dynamic-data file (waiting for new lines)
        while (readCount < pageSize) {
          line = reader.readLine()
          if (line == null) {
            // end of file, waiting for new ones
            Log.d(LTAG, "...end-of-file: waiting for more")
            Thread.sleep(1000)
          } else {
            val obj: T = objectFileProc.toObject(line!!)

            Log.d(LTAG, "...loaded ${obj}")

            objects.add(obj)
            objCount++
            readCount++
          }
        }

        reader.mark(readAheadLimit)

        // setup prev, next keys
        val prevKey = if (pageNumber > 0) pageNumber - 1 else null
        // use this for dynamic-data file (more lines added by other processes)
        val nextKey = pageNumber + 1

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
}