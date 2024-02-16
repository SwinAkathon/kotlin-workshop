package com.example.ecoms

import androidx.compose.ui.graphics.Color

data class AppConfig (
  val appColor: Color =
    //Color(144, 238, 144) // light-green
    Color(255, 241, 220)  // light yellow
  ,
  val appName : String = "Ecoms"
) {
  companion object {
    val props = AppConfig()
  }
}
