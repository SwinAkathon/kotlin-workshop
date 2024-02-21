package com.example.helloworld

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.helloworld.ui.theme.HelloworldTheme

/**
 * @overview Layout with row, column
 */
class MainActivity4Layout : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      HelloworldTheme {
       MyApp2()
      }
    }
  }
}

@Composable
fun MyApp2(modifier: Modifier = Modifier,
           names: List<String> = listOf("World", "Compose")
           ) {
  Column(
    modifier = modifier.padding(vertical = 4.dp)
  ) {
    for (name in names) {
      Greeting3(name)
    }
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
