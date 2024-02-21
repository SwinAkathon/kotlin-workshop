package com.example.helloworld

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.helloworld.ui.theme.HelloworldTheme

/**
 * @overview State persistence
 */
class MainActivity7StatePersistence : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      HelloworldTheme {
       MyApp5(modifier = Modifier.fillMaxSize())
      }
    }
  }
}

@Composable
fun MyApp5(modifier: Modifier = Modifier) {
  // state hoisted up from OnboardingScreen
  var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

  Surface(
    modifier = modifier
  ) {
    if (shouldShowOnboarding) {
      OnboardingScreen { shouldShowOnboarding = false }
    } else {
      Greetings2()
    }
  }
}

@Composable
fun Greetings2(modifier: Modifier = Modifier,
           names: List<String> = listOf("Android", "Compose")
           ) {
  Column(
    modifier = modifier.padding(vertical = 4.dp)
  ) {
    for (name in names) {
      Greeting6(name)
    }
  }
}

@Composable
fun Greeting6(name: String, modifier: Modifier = Modifier) {
  var expanded by rememberSaveable { mutableStateOf(false) }
  val extraPadding = if (expanded) 48.dp else 0.dp
  Surface(
    color = MaterialTheme.colorScheme.primary,
    modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
  ) {
    Row(modifier = Modifier.padding(24.dp)) {
      Column(modifier = Modifier
        .weight(1f)
        .padding(bottom = extraPadding)) {
        Text(text = "Hello ")
        Text(text = name)
      }
      ElevatedButton(
        onClick = { expanded = !expanded }
      ) {
        Text( if (expanded) "Show less" else "Show more")
      }
    }
  }
}
