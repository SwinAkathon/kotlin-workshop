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

    val colorEnabledBg = Color.White
    val colorDisabledBg = Color.LightGray
    val colorTitle: Color = Color.Blue
    val colorDrawerMenu: Color = Color.Blue
    val colorBottomNav: Color = Color.Blue
    val colorHeading: Color = Color.Blue

    val colorApp: Color =
      //Color(144, 238, 144) // light-green
      Color(255, 241, 220)  // light yellow

    val colorContentBorder: Color = Color.Gray

    val paddingContent = 16.dp
  }
}
