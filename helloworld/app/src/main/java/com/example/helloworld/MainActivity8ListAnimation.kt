package com.example.helloworld

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.helloworld.ui.theme.HelloworldTheme

/**
 * @overview List animation. Also customised the composables for reuse by later activities.
 */
class MainActivity8ListAnimation : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      HelloworldTheme {
       MyApp6(modifier = Modifier.fillMaxSize())
      }
    }
  }
}

@Composable
fun MyApp6(modifier: Modifier = Modifier,
           textStyle: TextStyle = MaterialTheme.typography.bodyMedium) {
  // state hoisted up from OnboardingScreen
  var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

  Surface(
    modifier = modifier
  ) {
    if (shouldShowOnboarding) {
      OnboardingScreen { shouldShowOnboarding = false }
    } else {
      Greetings3(textStyle = textStyle)
    }
  }
}

@Composable
fun Greetings3(modifier: Modifier = Modifier,
           names: List<String> = List(1000) {"$it"},
           textStyle: TextStyle = MaterialTheme.typography.bodyMedium)
{
  LazyColumn(
    modifier = modifier.padding(vertical = 4.dp)
  ) {
    items(names) { name ->
      Greeting7(name = name, textStyle = textStyle)
    }
  }
}

@Composable
fun Greeting7(name: String,
              modifier: Modifier = Modifier,
              textStyle: TextStyle = MaterialTheme.typography.bodyMedium) {
  var expanded by rememberSaveable { mutableStateOf(false) }

  // animation
  val extraPadding by animateDpAsState(
    if (expanded) 48.dp else 0.dp,
    animationSpec = spring( // spring animation effect
      dampingRatio = Spring.DampingRatioMediumBouncy,
      stiffness = Spring.StiffnessLow
    ),
    label = ""
  )

  Surface(
    color = MaterialTheme.colorScheme.primary,
    modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
  ) {
    Row(modifier = Modifier.padding(24.dp)) {
      Column(modifier = Modifier
        .weight(1f)
        .padding(bottom = extraPadding.coerceAtLeast(0.dp))) {
        Text(text = "Hello ")
        Text(text = name, style = textStyle)
      }
      ElevatedButton(
        onClick = { expanded = !expanded }
      ) {
        Text( if (expanded) "Show less" else "Show more")
      }
    }

  }
}
