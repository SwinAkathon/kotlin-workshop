package com.example.ecoms.modules.dashboard.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ecoms.modules.order.model.Order
import com.example.ecoms.modules.product.model.Product
import java.time.LocalDate

@Composable
fun Dashboard() {
  // Retrieve data from Room database
  val products = readProducts()// Get products from Room database
  val orders = readOrders()// Get orders from Room database

  Column(
    modifier = Modifier.padding(16.dp)
  ) {
    Text(text = "Products: ${products.size}")
    Text(text = "Orders: ${orders.size}")
    // Display other summary information about products and orders
  }
}

fun readOrders(): List<Order> {
  val currentDate = LocalDate.now()
  val orders = mutableListOf<Order>()
  (1 .. 20).forEach { id ->
    orders.add(Order(id, currentDate.plusDays(id.toLong())))
  }

  return orders
}

fun readProducts(): List<Product> {
  val productNames = listOf<String>(
    "Dell Vostro", "MacBook Pro", "ASUS Legend", "ThinkPad")
  val products = mutableListOf<Product>()
  var id = 1;
  productNames.forEach{ name ->
    products.add(Product(id++, name))
  }

  return products
}