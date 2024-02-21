package com.example.ecoms.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.example.ecoms.AppConfig.Companion.Blue
import com.example.ecoms.AppConfig.Companion.Chartreuse
import com.example.ecoms.AppConfig.Companion.LightBlue
import com.example.ecoms.AppConfig.Companion.Navy

private val DarkColorScheme = darkColorScheme(

  /* default: primary = Purple80,
  secondary = PurpleGrey80,
  tertiary = Pink80
  */

  // custom
  surface = Blue,
  onSurface = Navy,
  primary = Navy,
  onPrimary = Chartreuse,
  secondary = Navy,
  onSecondary = Chartreuse,
  tertiary = Navy,
  onTertiary = Chartreuse
)

private val LightColorScheme = lightColorScheme(
  /*default:
  primary = Purple40,
  secondary = PurpleGrey40,
  tertiary = Pink40*/

  // custom
  surface = Blue,
  onSurface = White,
  primary = LightBlue,
  onPrimary = Navy,
  secondary = LightBlue,
  onSecondary = Chartreuse,
  tertiary = LightBlue,
  onTertiary = Chartreuse

  /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun EcomsTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // Dynamic color is available on Android 12+
  dynamicColor: Boolean = true,
  content: @Composable () -> Unit
) {
  val colorScheme = when {
    dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
      val context = LocalContext.current
      if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    }

    darkTheme -> DarkColorScheme
    else -> LightColorScheme
  }
  val view = LocalView.current
  if (!view.isInEditMode) {
    SideEffect {
      val window = (view.context as Activity).window
      window.statusBarColor = colorScheme.primary.toArgb()
      WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
    }
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}