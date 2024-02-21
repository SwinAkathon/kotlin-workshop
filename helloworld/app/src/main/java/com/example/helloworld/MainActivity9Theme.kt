package com.example.helloworld

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.helloworld.ui.theme.HelloworldTheme

/**
 * @overview Theming and coloring. Also see changes in Theme.kt file.
 */
class MainActivity9Theme : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      HelloworldTheme {
       MyApp6(modifier = Modifier.fillMaxSize(),
         textStyle = MaterialTheme.typography.headlineMedium.copy(
           fontWeight = FontWeight.ExtraBold
          )
         )
      }
    }
  }
}

@Preview(
  showBackground = true,
  widthDp = 320,
  uiMode = UI_MODE_NIGHT_YES,
  name = "GreetingPreviewDark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun AppPreview() {
  HelloworldTheme {
    Greetings3()
  }
}

