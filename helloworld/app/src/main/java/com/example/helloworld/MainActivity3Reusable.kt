package com.example.helloworld

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.helloworld.ui.theme.HelloworldTheme

/**
 * @overview Reuse composables
 */
class MainActivity3Reusable : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      HelloworldTheme {
       MyApp(modifier = Modifier.fillMaxSize())
      }
    }
  }
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {
  Surface(
    modifier = modifier,
    color = MaterialTheme.colorScheme.background
  ) {
    Greeting2("Android")
  }
}