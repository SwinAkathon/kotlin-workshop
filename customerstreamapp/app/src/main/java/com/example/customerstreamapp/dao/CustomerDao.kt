package com.example.customerstreamapp.dao

import com.example.customerstreamapp.model.Customer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun getCustomers(): Flow<List<Customer>> = flow {
  // simulate
  val names = arrayOf("Alice", "Bob", "John", "Jane", "Andrew")
  val customers = List(50) {
    Customer(it+1,names.random() + (it+1))
  }

  emit(customers)

  // todo: handle pagination
}

