package com.example.customerstreamapp.model

import android.content.res.AssetManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.customerstreamapp.dao.CustomerPagingSource

class CustomerViewModel() : ViewModel() {
  // val customers: Flow<List<Customer>> = getCustomers()
  // use Paging to obtain objects

  lateinit var assetMan: AssetManager

  val customerFlow = Pager(PagingConfig(pageSize = 20)) {
    CustomerPagingSource(assetMan,"customers.csv")
  }.flow.cachedIn(viewModelScope)
}