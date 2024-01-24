package com.example.customerstreamapp.model

import androidx.lifecycle.ViewModel
import com.example.customerstreamapp.dao.getCustomers
import kotlinx.coroutines.flow.Flow

class CustomerViewModel : ViewModel() {
  val customers: Flow<List<Customer>> = getCustomers()
}