package com.example.helloworlddemo

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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.helloworlddemo.ui.theme.HelloworlddemoTheme

class MainActivity5State : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      HelloworlddemoTheme {
        App3(modifier = Modifier.fillMaxSize())
      }
    }
  }
}

@Composable
fun App3(modifier: Modifier = Modifier) {
  // A surface container using the 'background' color from the theme
  Surface(modifier = Modifier.fillMaxSize(),
    color = MaterialTheme.colorScheme.background) {
    Greeting4("Android workshop")
  }
}

@Composable
fun Greeting4(name: String, modifier: Modifier = Modifier,
              textStyle: TextStyle = MaterialTheme.typography.bodyMedium) {
  var expanded by rememberSaveable { mutableStateOf(false) }

  val extraPadding by animateDpAsState(
    if (expanded) 48.dp else 0.dp
    ,animationSpec = spring(
      dampingRatio = Spring.DampingRatioMediumBouncy,
      stiffness = Spring.StiffnessLow
    )
    , label = ""
  )

//  val extraPadding = if (expanded) 48.dp else 0.dp

  Surface(
    color = MaterialTheme.colorScheme.primary,
    modifier = modifier
      .wrapContentSize()
      .padding(vertical = 4.dp, horizontal = 8.dp)
  ) {
    Row(modifier = Modifier.padding(24.dp)) {
      Column(
        modifier = Modifier
          .weight(1f)
          .padding(bottom = extraPadding.coerceAtLeast(0.dp))
      ) {
        Text(text = "Hello ")
        Text(text = name, style = textStyle)
      }
      ElevatedButton(
        onClick = { expanded = !expanded }
      ) {
        Text(if (expanded) "Show less" else "Show more")
      }
    }
  }
}
