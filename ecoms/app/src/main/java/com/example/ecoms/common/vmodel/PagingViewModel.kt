package com.example.ecoms.common.vmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.ecoms.common.dao.DataSource
import kotlinx.coroutines.flow.Flow

/**
 * @overview A generic customised ViewModel that supports paging. It uses a Pager and PagingFileSource to
 * incrementally loads objects from a data source one page at a time.
 * The page size, which has the default value of PageSize, can be set through
 * an input parameter.
 */
open abstract class PagingViewModel<T: Any>(
  open var pagingConfig: PagingConfig = DefaultPagingConfig
) : ViewModel() {
  companion object {
    val DefaultPagingConfig = PagingConfig(
      pageSize = 5
      ,initialLoadSize = 3 * 5      // Number of items to load initially. Defaults to three times pageSize if not set.
      ,prefetchDistance = 2      // Number of items to prefetch, which helps with smooth scrolling.
      ,enablePlaceholders = false // Set to true to enable placeholder items.
    )
    val DefaultStreamPagingConfig = PagingConfig(
      pageSize = 2  // smaller page size for stream data
      ,initialLoadSize = 2    // same as page size (to reduce initial wait time)
      ,prefetchDistance = 1    // small enough to reduce loading, without disabling prefetch
      ,enablePlaceholders = false // Set to true to enable placeholder items.
    )
  }

  var countItems: MutableState<Int> = mutableStateOf(0)

  private val dataSourceEventHandler: (count: Int) -> Unit = {
      count -> handleDataSourceEvent(count)
  }

  /**
   * @effects handle a data source change event (e.g. after loading a new page of items)
   */
  open fun handleDataSourceEvent(count: Int) {
    countItems.value = count
  }

  // use Paging to obtain objects
  private lateinit var pagingDataSource: DataSource<T>

  private lateinit var flow: Flow<PagingData<T>>

  /**
   * @effects: sets #pagingDataSource to the parameter and initialises #flow to use
   * pagination to retrieve data objects.
   * Further, registers #dataSourceEventHandler to listen to data loading events of #pagingDataSource.
   */
  fun setPagingDataSource(pagingDataSource: DataSource<T>) {
    pagingDataSource.registerDataSourceListener(dataSourceEventHandler)
    this.pagingDataSource = pagingDataSource

    flow = Pager(pagingConfig) { pagingDataSource }
      .flow.cachedIn(viewModelScope)
  }

  fun getFlow(): Flow<PagingData<T>> {
    return flow
  }
}