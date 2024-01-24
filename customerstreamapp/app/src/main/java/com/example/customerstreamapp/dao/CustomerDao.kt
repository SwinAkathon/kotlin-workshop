package com.example.customerstreamapp.dao

import com.example.customerstreamapp.model.Customer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun getCustomers(): Flow<List<Customer>> = flow {
  // simulate
  val customers = listOf(Customer(1,"Alice"),
    Customer(2, "Bob")
    )
  emit(customers)

  // todo: handle pagination
}