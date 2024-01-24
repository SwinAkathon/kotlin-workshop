package com.example.customerstreamapp.dao

import android.content.Context
import android.content.res.AssetManager
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.customerstreamapp.model.Customer
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.FileReader

/**
 * @requires filePath is a valid path (in the src/main/assets directory) to a CSV file containing Customer data
 */
class CustomerPagingSource(private val assetMan: AssetManager, private val filePath: String) : PagingSource<Int, Customer>() {
  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Customer> {
    return try {
      // Open the CSV file
      val inputStream = assetMan.open(filePath)
      val reader = inputStream.bufferedReader()
      val customers = mutableListOf<Customer>()

      // Skip the header row if necessary
      reader.readLine()

      // Read lines and parse
      var line: String?
      while (reader.readLine().also { line = it } != null) {
        val tokens = line!!.split(",")
        // Assuming CSV format: id,name
        val customer = Customer(tokens[0].toInt(), tokens[1])
        customers.add(customer)
      }

      reader.close()

      LoadResult.Page(
        data = customers,
        prevKey = null, // Only paging forward
        nextKey = null // Assuming all data is loaded in one go
      )
    } catch (e: Exception) {
      LoadResult.Error(e)
    }
  }

  override fun getRefreshKey(state: PagingState<Int, Customer>): Int? {
    return null // Return null if data cannot be refreshed based on a position
  }
}