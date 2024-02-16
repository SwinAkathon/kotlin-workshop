package com.example.customerstreamapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.customerstreamapp.model.CustomerViewModel
import com.example.customerstreamapp.view.CustomerItem
import com.example.customerstreamapp.view.theme.CustomerStreamAppTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.tooling.preview.Preview

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
      custViewModel.assetMan = context.assets
    }

    val surfaceHeight = 0.8f

    setContent {
      CustomerStreamAppTheme {
        Box(
          modifier = Modifier
            .fillMaxWidth().fillMaxHeight()
            .border(width=3.dp,color= Color.Blue)
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
              // show two customer stream screens, sharing the same data source
              CustomerStreamScreen(context, custViewModel, 1f, 0.42f)
              CustomerStreamScreen(context, custViewModel, 1f, 0.7f)
            }
          }
        }
      }
    }
  }
}

@Composable
fun CustomerStreamScreen(context: Context,
                         custViewModel: CustomerViewModel,
                         maxWidth: Float,
                         maxHeight: Float
) {
  // shows the scrollbar on LazyColumn when number of items exceeds the height
  // not yet supported: val scrollState = rememberScrollState()

  val pagingItems = custViewModel.customerFlow.collectAsLazyPagingItems()

  Row(modifier = Modifier.padding(bottom=5.dp)) {
    // use row to add padding to the next item on the same surface
    LazyColumn(
      // not yet supported:    modifier = Modifier.verticalScroll(scrollState, reverseScrolling = true),
      modifier = Modifier
        .fillMaxWidth(maxWidth)
        .fillMaxHeight(maxHeight)
        .border(width=1.dp,color= Color.Red)
      ,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      items(pagingItems.itemCount) { index ->
        pagingItems[index]?.let {
          CustomerItem(customer = it, onCheckedChange = { isChecked ->
            // todo: Handle customer selection
            notify(context, it.toString())
          })
        }
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
