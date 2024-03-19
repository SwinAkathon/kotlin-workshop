package com.example.ecoms.modules.iot.humidity.view

import androidx.paging.PagingConfig
import com.example.ecoms.common.vmodel.PagingViewModel
import com.example.ecoms.modules.iot.humidity.model.Humidity

class HumidityViewModel(
  pagingConfig: PagingConfig = DefaultStreamPagingConfig) :
  PagingViewModel<Humidity>(pagingConfig) {
  // empty
}