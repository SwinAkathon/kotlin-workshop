package com.example.ecoms.common.dao

import androidx.paging.PagingSource

abstract class DataSource<T: Any>(): PagingSource<Int, T>() {
  abstract fun registerDataSourceListener(dataSourceListener: (count: Int) -> Unit)
}