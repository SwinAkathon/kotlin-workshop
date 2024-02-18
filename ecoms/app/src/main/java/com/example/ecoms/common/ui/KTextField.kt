package com.example.ecoms.common.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.example.ecoms.AppConfig

@Composable
fun KHeadingField(label: String? = null,
               modifier: Modifier = Modifier,
               value: String,
               onValueChange: ((String) -> Unit)? = null) {
  KTextField(
    label = label,
    modifier = modifier,
    value = value,
    enabled = false,
    onValueChange = onValueChange,
    colors = TextFieldDefaults.colors(
      unfocusedTextColor = AppConfig.colorHeading,
      focusedTextColor = AppConfig.colorHeading,
      disabledLabelColor = AppConfig.colorHeading,
      disabledContainerColor = AppConfig.colorDisabledBg,
      unfocusedContainerColor = AppConfig.colorEnabledBg,
      focusedContainerColor = AppConfig.colorEnabledBg,
      disabledTextColor = AppConfig.colorHeading,
    ),
    style = AppConfig.styleHeading
  )
}

@Composable
fun KTextField(label: String? = null,
               modifier: Modifier = Modifier,
               value: String,
               enabled: Boolean = true,
               onValueChange: ((String) -> Unit)? = null,
               colors : TextFieldColors = TextFieldDefaults.colors(),
               style: TextStyle = AppConfig.styleNormal) {
  TextField(
    label = {
      if (label != null) {
        Text(label, style = style)
      }
    },
    modifier = modifier,
    value = value,
    onValueChange = { newVal: String ->
      if (onValueChange != null) {
        onValueChange(newVal)
      }
    },
    //    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    enabled = enabled,
    colors = colors,
    textStyle = style
  )
}