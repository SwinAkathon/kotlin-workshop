package com.example.helloworlddemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.helloworlddemo.ui.theme.HelloworlddemoTheme

/**
 * @overview Maintain state with hoisting
 */
class MainActivity6StateHoisting : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      HelloworlddemoTheme {
       MyApp4(modifier = Modifier.fillMaxSize())
      }
    }
  }
}

@Composable
fun MyApp4(modifier: Modifier = Modifier) {
  // state hoisted up from OnboardingScreen
  var shouldShowOnboarding by remember { mutableStateOf(true) }

  Surface(
    modifier = modifier
  ) {
    if (shouldShowOnboarding) {
      OnboardingScreen { shouldShowOnboarding = false }
    } else {
      Greetings()
    }
  }
}

@Composable
fun Greetings(modifier: Modifier = Modifier,
           names: List<String> = listOf("Android", "Compose")
           ) {
  Column(
    modifier = modifier.padding(vertical = 4.dp)
  ) {
    for (name in names) {
      Greeting5(name)
    }
  }
}

@Composable
fun OnboardingScreen(modifier: Modifier = Modifier,
                     onContinueClicked: () -> Unit) {
  Column(
    modifier = modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text("Welcome to the Basics Codelab!")
    Button(
      modifier = Modifier.padding(vertical = 24.dp),
      onClick = onContinueClicked
    ) {
      Text("Continue")
    }
  }
}

@Composable
fun Greeting5(name: String, modifier: Modifier = Modifier) {
  var expanded by remember { mutableStateOf(false) }
  val extraPadding = if (expanded) 48.dp else 0.dp
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
        onClick = { expanded = !expanded }
      ) {
        Text( if (expanded) "Show less" else "Show more")
      }
    }

  }
}
