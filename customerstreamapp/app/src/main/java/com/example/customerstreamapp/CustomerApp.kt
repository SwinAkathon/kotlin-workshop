package com.example.customerstreamapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.customerstreamapp.model.CustomerViewModel
import com.example.customerstreamapp.view.theme.CustomerStreamAppTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      CustomerStreamAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          CustomerStreamScreen()
        }
      }
    }
  }
}

@Composable
fun CustomerStreamScreen(viewModel: CustomerViewModel = viewModel()) {
  val customers by viewModel.customers.collectAsState(initial = emptyList())
  LazyColumn {
    items(customers) { customer ->
      Text("ID: ${customer.id}, Name: ${customer.name}")
    }
  }
}
