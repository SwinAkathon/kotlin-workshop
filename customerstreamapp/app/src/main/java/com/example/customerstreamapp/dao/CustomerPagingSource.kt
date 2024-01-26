package com.example.customerstreamapp.dao

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.customerstreamapp.model.Customer
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.FileReader

/**
 * @requires filePath is a valid path (in the src/main/assets directory) to a CSV file containing Customer data
 */
class CustomerPagingSource(private val assetMan: AssetManager, private val filePath: String) :
  PagingSource<Int, Customer>() {
  private lateinit var reader: BufferedReader

  private val LTAG = CustomerPagingSource::class.simpleName

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Customer> {
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

      // Read lines and parse
      val customers = mutableListOf<Customer>()
      var line: String?
      var readCount = 0
      while ((reader.readLine().also { line = it } != null) && readCount < pageSize) {
        val tokens = line!!.split(",")
        // record format: id,name
        val customer = Customer(tokens[0].toInt(), tokens[1])
        customers.add(customer)
        readCount++
      }

      reader.mark(readAheadLimit)

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

  override fun getRefreshKey(state: PagingState<Int, Customer>): Int? {
    return null // Return null if data cannot be refreshed based on a position
  }
}