package com.example.customerstreamapp.model

import android.content.res.AssetManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.customerstreamapp.dao.CustomerPagingSource

class CustomerViewModel() : ViewModel() {
  // use Paging to obtain objects
  val pageSize = 5
  private val filePath = "customers.csv"

  lateinit var assetMan: AssetManager

  val customerFlow = Pager(PagingConfig(pageSize = this.pageSize)) {
    CustomerPagingSource(assetMan,filePath)
  }.flow.cachedIn(viewModelScope)
}