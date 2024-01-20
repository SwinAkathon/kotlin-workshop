package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import com.example.calculator.view.CalculatorScreen
import com.example.calculator.view.theme.CalculatorTheme
import com.example.calculator.model.BasicCalculator

class CalculatorAct : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // initialise a calculator object
    val calc = BasicCalculator()

    setContent {
      val context = LocalContext.current;

      CalculatorTheme {
        // initialise the calculator screen
        CalculatorScreen(context, calc)
      }
    }
  }
}
