package com.example.helloworlddemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.helloworlddemo.ui.theme.HelloworlddemoTheme

class MainActivity4GridLayout : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      HelloworlddemoTheme {
        App2(modifier = Modifier.fillMaxSize())
      }
    }
  }
}

@Composable
fun App2(modifier: Modifier = Modifier) {
  // A surface container using the 'background' color from the theme
  Surface(modifier = Modifier.fillMaxSize(),
    color = MaterialTheme.colorScheme.background) {
    Greeting3("Android workshop")
  }
}

@Composable
fun Greeting3(name: String, modifier: Modifier = Modifier) {
  Surface(
    color = MaterialTheme.colorScheme.primary,
    modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
  ) {
    Row(modifier = Modifier.padding(24.dp)) {
      Column(modifier = Modifier.weight(1f)) {
        Text(text = "Hello ")
        Text(text = name)
      }
      ElevatedButton(
        onClick = { /* TODO */ }
      ) {
        Text("Show more")
      }
    }
  }
}

