package com.example.helloworld

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.helloworld.ui.theme.HelloworldTheme

/**
 * @overview Theming and coloring. Also see changes in Theme.kt file.
 */
class MainActivity10Theme : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      HelloworldTheme {
       MyApp7(modifier = Modifier.fillMaxSize(),
         textStyle = MaterialTheme.typography.headlineMedium.copy(
           fontWeight = FontWeight.ExtraBold
          )
         )
      }
    }
  }
}

@Composable
fun MyApp7(modifier: Modifier = Modifier,
           textStyle: TextStyle = MaterialTheme.typography.bodyMedium) {
  // state hoisted up from OnboardingScreen
  var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

  Surface(
    modifier = modifier
  ) {
    if (shouldShowOnboarding) {
      OnboardingScreen { shouldShowOnboarding = false }
    } else {
      Greetings4(textStyle = textStyle)
    }
  }
}

@Composable
fun Greetings4(modifier: Modifier = Modifier,
               names: List<String> = List(1000) {"$it"},
               textStyle: TextStyle = MaterialTheme.typography.bodyMedium)
{
  LazyColumn(
    modifier = modifier.padding(vertical = 4.dp)
  ) {
    items(names) { name ->
      Greeting8(name = name, textStyle = textStyle)
    }
  }
}

@Composable
fun Greeting8(name: String,
              modifier: Modifier = Modifier,
              textStyle: TextStyle = MaterialTheme.typography.bodyMedium) {
  Card(
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.primary
    ),
    modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
  ) {
    CardContent(name, textStyle)
  }
}

@Composable
fun CardContent(name: String, textStyle: TextStyle) {
  var expanded by rememberSaveable { mutableStateOf(false) }

  // animation
  Row(
    modifier = Modifier
      .padding(12.dp)
      .animateContentSize(
        animationSpec = spring(
          dampingRatio = Spring.DampingRatioMediumBouncy,
          stiffness = Spring.StiffnessLow
        )
      )
  ) {
    Column(
      modifier = Modifier
        .weight(1f)
        .padding(12.dp)
    ) {
      Text(text = "Hello, ")
      Text(
        text = name, style = MaterialTheme.typography.headlineMedium.copy(
          fontWeight = FontWeight.ExtraBold
        )
      )
      if (expanded) {
        Text(
          text = ("Composem ipsum color sit lazy, " +
              "padding theme elit, sed do bouncy. ").repeat(4),
        )
      }
    }
    IconButton(onClick = { expanded = !expanded }) {
      Icon(
        imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
        contentDescription = if (expanded) {
          stringResource(R.string.show_less)
        } else {
          stringResource(R.string.show_more)
        }
      )
    }
  }
}
