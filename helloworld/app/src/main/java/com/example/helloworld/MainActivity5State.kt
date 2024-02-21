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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.helloworld.ui.theme.HelloworldTheme

/**
 * @overview Maintain state (basic)
 */
class MainActivity5State : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      HelloworldTheme {
       MyApp3()
      }
    }
  }
}

@Composable
fun MyApp3(modifier: Modifier = Modifier,
           names: List<String> = listOf("Android", "Compose")
           ) {
  Column(
    modifier = modifier.padding(vertical = 4.dp)
  ) {
    for (name in names) {
      Greeting4(name)
    }
  }
}

@Composable
fun Greeting4(name: String, modifier: Modifier = Modifier) {
  val expanded = remember { mutableStateOf(false) }
  val extraPadding = if (expanded.value) 48.dp else 0.dp
  Surface(
    color = MaterialTheme.colorScheme.primary,
    modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
  ) {
    Row(modifier = Modifier.padding(24.dp)) {
      Column(modifier = Modifier.weight(1f).padding(bottom = extraPadding)) {
        Text(text = "Hello ")
        Text(text = name)
      }
      ElevatedButton(
        onClick = { expanded.value = !expanded.value }
      ) {
        Text( if (expanded.value) "Show less" else "Show more")
      }
    }

  }
}
