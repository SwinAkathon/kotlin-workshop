package com.example.customerstreamapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.customerstreamapp.model.CustomerViewModel
import com.example.customerstreamapp.view.CustomerItem
import com.example.customerstreamapp.view.theme.CustomerStreamAppTheme

class CustomerAct : ComponentActivity() {
  private lateinit var custViewModel: CustomerViewModel
  private lateinit var context: Context

  private val LTAG = CustomerAct::class.simpleName

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    context = applicationContext

    // initialise ViewModel (once)
    if (!::custViewModel.isInitialized) {
      custViewModel =
        ViewModelProvider(this).get(CustomerViewModel::class.java)
      Log.d(LTAG, "view model initialised")
    }

    custViewModel.assetMan = context.assets

    setContent {
      CustomerStreamAppTheme {
        Box(modifier = Modifier
          .fillMaxWidth()
          //.border(width=3.dp,color= Color.Blue)
          .padding(10.dp)
          ,contentAlignment = Alignment.Center) {
          // A surface container using the 'background' color from the theme
          Surface(
            modifier = Modifier
              .fillMaxWidth(0.8f)
              .fillMaxHeight(0.5f),
            color = MaterialTheme.colorScheme.secondaryContainer) {
            CustomerStreamScreen(context, custViewModel)
          }
        }
      }
    }
  }
}

@Composable
fun CustomerStreamScreen(context: Context, custViewModel: CustomerViewModel) {
  // shows the scrollbar on LazyColumn when number of items exceeds the height
  // not yet supported: val scrollState = rememberScrollState()

  val pagingItems = custViewModel.customerFlow.collectAsLazyPagingItems()

  LazyColumn(
    // not yet supported:    modifier = Modifier.verticalScroll(scrollState, reverseScrolling = true),
    horizontalAlignment = Alignment.CenterHorizontally) {
    items(pagingItems.itemCount) { index ->
      pagingItems[index]?.let {
        CustomerItem(customer = it, onCheckedChange = { isChecked ->
          // todo: Handle customer selection
          notify(context, it.toString())
        })
      }
    }

    // display progress indicator
//    customers.apply {
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
