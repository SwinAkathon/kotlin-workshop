package com.example.ecoms.modules.dashboard.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ecoms.AppConfig
import com.example.ecoms.common.ui.KHeadingField
import com.example.ecoms.modules.product.view.ProductViewModel

private val LTAG = "DashBoard"

@Composable
fun DashBoard(navController: NavController) {

  // obtain the same ProductViewModel instance associated with the context
  val productViewModel: ProductViewModel = viewModel(
    navController.getViewModelStoreOwner(navController.graph.id)
  )

//  Log.d(LTAG, "view model retrieved: ${productViewModel} (${productViewModel.hashCode()})")

  val productCount: Int by remember {
    mutableStateOf(productViewModel.countItems.value)
  }

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