package com.example.ecoms.common.vmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.ecoms.common.dao.PagingFileSource
import kotlinx.coroutines.flow.Flow

/**
 * @overview A generic customised ViewModel that supports paging. It uses a Pager and PagingFileSource to
 * incrementally loads objects from a data source one page at a time.
 * The page size, which has the default value of PageSize, can be set through
 * an input parameter.
 */
open abstract class PagingViewModel<T: Any>(
  protected val pageSize: Int = PageSize,
) : ViewModel() {
  companion object {
    const val PageSize: Int  = 5
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
  private lateinit var pagingDataSource: PagingFileSource<T>

  private lateinit var flow: Flow<PagingData<T>>

  /**
   * @effects: sets #pagingDataSource to the parameter and initialises #flow to use
   * pagination to retrieve data objects.
   * Further, registers #dataSourceEventHandler to listen to data loading events of #pagingDataSource.
   */
  fun setPagingDataSource(pagingDataSource: PagingFileSource<T>) {
    pagingDataSource.registerDataSourceListener(dataSourceEventHandler)
    this.pagingDataSource = pagingDataSource

    flow = Pager(PagingConfig(pageSize = this.pageSize)) { pagingDataSource }
      .flow.cachedIn(viewModelScope)
  }

  fun getFlow(): Flow<PagingData<T>> {
    return flow
  }
}