package com.example.ecoms.common.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.example.ecoms.AppConfig

@Composable
fun KHeadingField(label: String? = null,
               modifier: Modifier = Modifier,
               value: String,
               onValueChange: ((String) -> Unit)? = null) {
  val containerColor = MaterialTheme.colorScheme.secondaryContainer
  var textColor = MaterialTheme.colorScheme.onSecondaryContainer
  KTextField(
    label = label,
    modifier = modifier,
    value = value,
    enabled = false,
    onValueChange = onValueChange,
    colors = TextFieldDefaults.colors(
      focusedContainerColor = containerColor,
      unfocusedContainerColor = containerColor,
      disabledContainerColor = containerColor,
      focusedTextColor = textColor,
      unfocusedTextColor = textColor,
      disabledTextColor = textColor,
      disabledLabelColor = textColor,
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