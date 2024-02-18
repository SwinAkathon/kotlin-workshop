package com.example.ecoms.modules.product.view

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.ecoms.AppConfig
import com.example.ecoms.modules.product.dao.ProductPagingSource
import com.example.ecoms.ui.theme.EcomsTheme

private lateinit var myViewModel: ProductViewModel
private val LTAG = "ProductScreen"

@Composable
fun ProductScreen(navController: NavController) {
  val surfaceHeight = 1f
  val context: Context = navController.context

  // initialise ViewModel (once)
  if (!::myViewModel.isInitialized) {
    //myViewModel = viewModel()
    myViewModel = viewModel(
      navController.getViewModelStoreOwner(navController.graph.id))

//    Log.d(LTAG, "view model retrieved: ${myViewModel} (${myViewModel.hashCode()})")

    myViewModel.setPagingDataSource(ProductPagingSource(context.assets))
  }

  EcomsTheme {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .border(width = 1.dp, color = AppConfig.colorContentBorder)
        .padding(10.dp), contentAlignment = Alignment.TopCenter
    ) {
      // A surface container using the 'background' color from the theme
      Surface(
        modifier = Modifier
          .fillMaxWidth(surfaceHeight)
          .wrapContentHeight()
        ,color = MaterialTheme.colorScheme.secondaryContainer
      ) {
        Column {
          // title
          Text("Products",
            color = Color.Blue,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            fontSize = TextUnit(32f, TextUnitType.Sp))
          // content
          ProductItemsScreen(context, myViewModel)
        }
      }
    }
  }
}

@Composable
fun ProductItemsScreen(context: Context,
                       viewModel: ProductViewModel,
                       maxWidth: Float = 1f,
                       maxHeight: Float = 1f
) {
  // shows the scrollbar on LazyColumn when number of items exceeds the height
  // not yet supported: val scrollState = rememberScrollState()

  val pagingItems = viewModel.getFlow().collectAsLazyPagingItems()

  Row(modifier = Modifier.padding(bottom=5.dp)) {
    // use row to add padding to the next item on the same surface
    LazyColumn(
      // not yet supported:    modifier = Modifier.verticalScroll(scrollState, reverseScrolling = true),
      modifier = Modifier
        .fillMaxWidth(maxWidth)
        .fillMaxHeight(maxHeight)
//        .border(width=1.dp,color= Color.Red)
      ,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      items(pagingItems.itemCount) { index ->
        pagingItems[index]?.let {
          ProductItem(product = it, onCheckedChange = { isChecked ->
            // todo: Handle product selection
            notify(context, it.toString())
          })
        }
      }
    }
    // display progress indicator
//    products.apply {
//      when {
//        loadState.refresh is LoadState.Loading -> {
//          item { CircularProgressIndicator() } // Show at top when initially loading
//        }
//        loadState.append is LoadState.Loading -> {
//          item { CircularProgressIndicator() } // Show at bottom when loading more items
//        }
//      }
//    }
  }
}

fun notify(context: Context, msg: String) {
  Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}
