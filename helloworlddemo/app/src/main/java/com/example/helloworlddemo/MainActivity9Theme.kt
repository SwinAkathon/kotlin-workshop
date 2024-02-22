package com.example.helloworlddemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.helloworlddemo.ui.theme.HelloworlddemoTheme

/**
 * @overview Theming and coloring. Also see changes in Theme.kt file.
 */
class MainActivity9Theme : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      HelloworlddemoTheme {
       App4(modifier = Modifier.fillMaxSize(),
         textStyle = MaterialTheme.typography.headlineMedium.copy(
           fontWeight = FontWeight.ExtraBold
          )
         )
      }
    }
  }
}


