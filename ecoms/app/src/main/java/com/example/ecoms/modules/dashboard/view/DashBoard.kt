package com.example.ecoms.modules.dashboard.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.ecoms.AppConfig
import com.example.ecoms.common.ui.KHeadingField
import com.example.ecoms.modules.product.dao.ProductSource.Companion.products

private val LTAG = "DashBoard"

@Composable
fun DashBoard(navController: NavController) {

  val productCount = products.size;

  // todo(similar to productCount)
  val orderCount = 0

  Column(
    modifier = Modifier.padding(AppConfig.paddingContent)
      .fillMaxWidth()
  ) {
    KHeadingField(
      value = "Product: ${productCount}",
      modifier = Modifier.align(Alignment.CenterHorizontally)
    )
    KHeadingField(
      value = "Orders: ${orderCount}",
      modifier = Modifier.align(Alignment.CenterHorizontally)
    )
  }
}