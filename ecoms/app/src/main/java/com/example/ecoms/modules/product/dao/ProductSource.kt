package com.example.ecoms.modules.product.dao

import com.example.ecoms.modules.product.model.Product

class ProductSource () {
    // customise this for each domain class type
  companion object {
    val products: List<Product> = readProducts()
      private fun readProducts(): List<Product> {
        return List(100) {
          val id = it+1
          Product(id, "Product ${id}")
        }
      }
  }
}
