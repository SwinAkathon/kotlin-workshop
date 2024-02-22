package com.example.helloworlddemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.helloworlddemo.ui.theme.HelloworlddemoTheme

class MainActivity2Tweak : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      HelloworlddemoTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background) {
          Greeting2("Android workshop")
        }
      }
    }
  }
}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
  Surface(
    color = MaterialTheme.colorScheme.primary,
    modifier = modifier.wrapContentSize(align = Alignment.Center)) {
    Text(
      text = "Hello $name!",
      modifier = modifier.padding(24.dp)
    )
  }
}
