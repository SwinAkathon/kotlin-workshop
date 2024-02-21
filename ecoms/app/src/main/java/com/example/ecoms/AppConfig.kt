package com.example.ecoms

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class AppConfig {
  companion object {
    const val appName : String = "Ecoms"
    val styleHeading = TextStyle(fontSize = 35.sp)
    val styleNormal = TextStyle(fontSize = 14.sp)
    val styleTitle = TextStyle(fontSize = 35.sp)
    val styleDrawerMenuTitle = TextStyle(fontSize = 25.sp)
    val styleBtnText = TextStyle(fontSize = 25.sp)

    // colors used by Theme.kt
    // COMPOSABLES MUST NOT DIRECTLY USE COLORS HERE!
    val Navy = Color(0xFF073042)
    val Blue = Color(0xFF4285F4)
    val LightBlue = Color(0xFFD7EF12)
    val Chartreuse = Color(0xFFEFF7CF)

    val paddingContent = 16.dp
  }
}
