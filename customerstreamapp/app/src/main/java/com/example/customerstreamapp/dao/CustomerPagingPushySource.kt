package com.example.customerstreamapp.dao

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.customerstreamapp.model.Customer
import java.io.ObjectInputStream

/**
 * @overview
 *
 * @requires inputStream represents a input stream of Customer objects
 */
class CustomerPagingPushySource(private val inputStream: ObjectInputStream) :
  PagingSource<Int, Customer>() {
  private val LTAG = CustomerPagingPushySource::class.simpleName

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Customer> {
    return try {
      val pageNumber = params.key ?: 0
      val pageSize = params.loadSize
      val readAheadLimit = 1024

      Log.d(LTAG, "loading page ${pageNumber} (pageSize: ${pageSize})...")
      // load objects for the current page
      // Read lines and parse
      val customers = mutableListOf<Customer>()
      var obj: Any?
      var readCount = 0
      while ((inputStream.defaultReadObject().also { obj = it } != null)) {
        // record format: id,name
        if (obj is Customer) {
          Log.d(LTAG, "...object ${obj}")
          customers.add(obj as Customer)
          readCount++

          if (readCount == pageSize) {
            refresh() // notify screen to refresh
          }
        }
      }

      // setup prev, next keys
      val prevKey = if (pageNumber > 0) pageNumber - 1 else null
      val nextKey = if (customers.isNotEmpty()) pageNumber + 1 else null

      Log.d(LTAG, "...read count (${readCount}); previous: ${prevKey}; next: ${nextKey}...")

      return LoadResult.Page(
        data = customers,
        prevKey = prevKey,
        nextKey = nextKey
      )
    } catch (e: Exception) {
      LoadResult.Error(e)
    }
  }

  private fun refresh() {
    TODO("Not yet implemented")
  }

  override fun getRefreshKey(state: PagingState<Int, Customer>): Int? {
    return null // Return null if data cannot be refreshed based on a position
  }
}
