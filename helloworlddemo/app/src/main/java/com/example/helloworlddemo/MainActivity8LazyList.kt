package com.example.helloworlddemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.example.helloworlddemo.ui.theme.HelloworlddemoTheme

class MainActivity8LazyList : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      HelloworlddemoTheme {
        App4(modifier = Modifier.fillMaxSize())
      }
    }
  }
}

@Composable
fun App4(modifier: Modifier = Modifier,
         textStyle: TextStyle = MaterialTheme.typography.bodyMedium) {
  // A surface container using the 'background' color from the theme
  Surface(modifier = Modifier.fillMaxSize(),
    color = MaterialTheme.colorScheme.background) {
    Greetings(modifier = modifier, textStyle = textStyle)
  }
}

@Composable
fun Greetings(modifier: Modifier = Modifier,
              names: List<String> = List(1000) { "${it+1}"},
              textStyle: TextStyle = MaterialTheme.typography.bodyMedium) {
  LazyColumn {
    items(items = names) { name ->
      Greeting4(name = name, modifier = modifier, textStyle = textStyle)
    }
  }
}
