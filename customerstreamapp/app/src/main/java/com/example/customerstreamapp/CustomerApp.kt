package com.example.customerstreamapp

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.customerstreamapp.model.CustomerViewModel
import com.example.customerstreamapp.view.CustomerItem
import com.example.customerstreamapp.view.theme.CustomerStreamAppTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val context = applicationContext
    setContent {
      CustomerStreamAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          CustomerStreamScreen(context)
        }
      }
    }
  }
}

@Composable
fun CustomerStreamScreen(context: Context) {
//  val customers by viewModel.customers.collectAsState(initial = emptyList())
  val viewModel: CustomerViewModel = viewModel()
  viewModel.assetMan = context.assets

  val customers = viewModel.customerFlow.collectAsLazyPagingItems()

  LazyColumn {
    items(customers.itemCount) { index ->
//      Text("ID: ${customer.id}, Name: ${customer.name}")
      customers[index]?.let {
        CustomerItem(customer = it, onCheckedChange = { isChecked ->
          // todo: Handle customer selection
          notify(context, it.toString())
        })
      }
    }

    customers.apply {
      when {
        loadState.refresh is LoadState.Loading -> {
          item { CircularProgressIndicator() } // Show at top when initially loading
        }
        loadState.append is LoadState.Loading -> {
          item { CircularProgressIndicator() } // Show at bottom when loading more items
        }
      }
    }
  }
}

fun notify(context: Context, msg: String) {
  Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}
