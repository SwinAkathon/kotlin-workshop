package com.example.calculator.view

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculator.model.BasicCalculator
import com.example.calculator.model.CalcModel

/**
 * Defines a calculator button as Composable
 */
@Composable
fun CalcButton(text: String, modifier: Modifier, context: Context, onClick: () -> Unit) {
  Button(
    modifier = modifier,
    onClick = onClick) {
    Text(text = text, style = btnTextStyle)
  }
}

/**
 * Represents a numeric text field for the Calculator input
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalcTextField(label: String,
                  modifier: Modifier,
                  inputValue: String,
                  onValueChange: (String) -> Unit) {
  val isEnabled = false
  val enabledBgColor = Color.White
  val disabledBgColor = Color.LightGray

  TextField(
    label = { Text(label, style = textStyle) },
    modifier = modifier,
    value = inputValue,
    onValueChange = { newVal -> onValueChange(newVal) },
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    enabled = isEnabled,
    colors = TextFieldDefaults.textFieldColors(
      unfocusedTextColor = Color.Blue,
      focusedTextColor = Color.Blue,
      disabledLabelColor = Color.Blue,
      containerColor = if (isEnabled) enabledBgColor else disabledBgColor,
      disabledTextColor = Color.Blue,
    ),
    textStyle = textStyle
  )
}

//fun CalcTextField(label: String, inputValue: TextFieldValue,
//                  onValueChange: (TextFieldValue) -> Unit) {
//  TextField(
//    label = { Text(label) },
//    value = inputValue,
//    onValueChange = { newVal ->
//                        onValueChange(newVal.copy(selection = TextRange(newVal.text.length)))
//                    },
//    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//  )
//}

@Composable
fun CalculatorScreen(context: Context, calc: BasicCalculator, calcModel: CalcModel) {
  // (invisible) Outer box, representing the entire screen
  Box(modifier =
//    Modifier.fillMaxSize(),
    Modifier.wrapContentSize(),
    contentAlignment = Alignment.Center // Align children to the center
  ) {
    Surface(  // Calculator surface
      modifier = Modifier
        .border(
          width = Props.getValueAs<Int>(Prop.OuterBorderSize)!!.dp,
          color = Color.Blue,
          shape = RoundedCornerShape(Props.getValueAs<Int>(Prop.OuterBorderRoundedCorner)!!.dp)
        )
        .padding(Props.getValueAs<Int>(Prop.OuterBorderPadding)!!.dp)
//        .fillMaxWidth(Props.getValueAs(Prop.WidthRatio)!!)
//        .fillMaxHeight(Props.getValueAs(Prop.HeightRatio)!!)
        .wrapContentSize()
      ,
      color = MaterialTheme.colorScheme.background,
    ) {
      // Column of rows
      Column(
        modifier =
          Modifier.fillMaxWidth(),
//          Modifier.wrapContentSize(),
        verticalArrangement =
        Arrangement.spacedBy(
          Props.getValueAs<Int>(Prop.RowSpacing)!!.dp,
          Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // Input row
        // shared calculator result (to be passed around between Calculator and
        // the Composables in CalculatorScreen
        Row(modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.Center)
        {
          CalcTextField("Enter input:",
            Modifier.fillMaxWidth(),
            calcModel.result.value) { s ->
            run {
              calc.setInput(s)
              calcModel.result.value = s
            }
          }
        }
        // Operators rows
        calcModel.keyPads.value.forEach { (row, keyPads) ->
          Row( // a button row
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement =
            Arrangement.spacedBy(
              Props.getValueAs<Int>(Prop.ButtonSpacing)!!.dp,
              Alignment.CenterHorizontally
            ),
            verticalAlignment = Alignment.CenterVertically
          ) {
            keyPads.forEach { key ->
              CalcButton(
                text = key,
                modifier = Modifier.weight(1f), // equal width distribution in each row
                context = context
              ) {
                try {
                  calc.input(key)
                  calcModel.result.value = calc.resultStr
                } catch (e: Error) {  // key not supported
                  displayMessage(context, e.message)
                }
              }
            }
          }
        }
      }
    }
  }
}

fun displayMessage(context: Context, message: String?) {
  Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

// Property name
enum class Prop {
  SizeRatio,
  ButtonSpacing,
  RowSpacing,
  OuterBorderSize,
  OuterBorderRoundedCorner,
  OuterBorderPadding,
  WidthRatio,
  HeightRatio
}

// Properties
val Props = mapOf<Prop, Any>(
  Prop.OuterBorderRoundedCorner to 20,
  Prop.OuterBorderSize to 4,
  Prop.OuterBorderPadding to 7,
  Prop.SizeRatio to 0.8f,
  Prop.WidthRatio to 0.9f,
  Prop.HeightRatio to 0.6f,
  Prop.ButtonSpacing to 3,
  Prop.RowSpacing to 3
)

val textStyle = TextStyle(fontSize = 20.sp)
val btnTextStyle = TextStyle(fontSize = 18.sp)

/**
 * @effects generic function for Map<String, Any> to get and cast value of a given type
 */
inline fun <reified T> Map<Prop, Any>.getValueAs(key: Prop, defaultValue: T? = null): T? {
  val value = this[key]
  return if (value is T) value else defaultValue
}
