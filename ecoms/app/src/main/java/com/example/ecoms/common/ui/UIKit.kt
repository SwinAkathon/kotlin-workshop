package com.example.ecoms.common.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object UIKit {
  //
  @Composable
  fun LoadingView() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
      CircularProgressIndicator(color= Color.Green)
    }
  }

  @Composable
  fun LoadingItem() {
    Box(modifier = Modifier
      .fillMaxWidth()
      .padding(16.dp), contentAlignment = Alignment.Center) {
      CircularProgressIndicator()
    }
  }

  @Composable
  fun SimplePageDivider() {
    Divider(color = Color.Gray,
      thickness = 1.dp,
      modifier = Modifier.fillMaxWidth())
  }

  @Composable
  fun LabelledDivider(label: String, fontSize: TextUnit = 12.sp,
                      color: Color = Color.Gray,
                      thickness: Float = 1f) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
      Box(modifier = Modifier.weight(1f)) {
        Divider(color = color, thickness = thickness.dp)
      }
      Text(
        text = label,
        fontSize = fontSize,
        modifier = Modifier.padding(horizontal = 8.dp),
        color = color
      )
      Box(modifier = Modifier.weight(1f)) {
        Divider(color = color, thickness = thickness.dp)
      }
    }
  }

  @Composable
  fun ErrorItem(error: Throwable, onRetry: () -> Unit = {}) {
    Column(modifier = Modifier
      .fillMaxWidth()
      .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
      Text(text = "Error: ${error.localizedMessage}")
      Button(onClick = onRetry) {
        Text("Retry")
      }
    }
  }
}

