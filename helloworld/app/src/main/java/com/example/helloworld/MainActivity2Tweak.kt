package com.example.helloworld

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.helloworld.ui.theme.HelloworldTheme

/**
 * @overview Tweak the UI
 */
class MainActivity2Tweak : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      HelloworldTheme {
        Greeting2("Android")
      }
    }
  }
}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
  // A surface container using the 'background' color from the theme
  Surface(modifier = Modifier.fillMaxSize(),
    color = MaterialTheme.colorScheme.primary) {
    Text(
      text = "Hello $name!",
      modifier = modifier.padding(24.dp)
    )
  }
}