package com.example.calculator

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import com.example.calculator.model.BasicCalculator
import com.example.calculator.model.KeyPadsLandscape
import com.example.calculator.model.KeyPadsPortrait
import com.example.calculator.view.CalculatorScreen
import com.example.calculator.view.theme.CalculatorTheme

class CalculatorAct : ComponentActivity() {
  // kep a MutableState of the keyPads to present, based on the Device orientation
  // which keyPads structure to use is defined in the calculator's model and is retrieved
  // at run time by the function onConfigurationChanged
  private val keyPadsFlex = mutableStateOf<Map<Int, Array<String>>>(mapOf())

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // initialise a calculator object
    val calc = BasicCalculator()

    setContent {
      val context = LocalContext.current;

      CalculatorTheme {
        // initialise the calculator screen
        keyPadsFlex.value = getKeyPadsConfiguration(resources.configuration)
        // pass keyPadsFlex into screen so that it is recomposed when keyPadsFlex is updated
        // at run time, when device rotates
        CalculatorScreen(context, keyPadsFlex, calc)
      }
    }
  }

  /**
   * Invoked when device has rotated
   */
  override fun onConfigurationChanged(newConfig: Configuration) {
    super.onConfigurationChanged(newConfig)
    // determines the keyPads map suitable for the orientation
    // update keyPadsFlex to cause Recomposition of the screen
    keyPadsFlex.value = getKeyPadsConfiguration(newConfig)
  }

  private fun getKeyPadsConfiguration(deviceCfg: Configuration): Map<Int, Array<String>> {
    val orient = deviceCfg.orientation
    if (orient == Configuration.ORIENTATION_PORTRAIT) {
      // keypad arrangement portrait orientation
      return KeyPadsPortrait
    } else {
      // keypad arrangement landscape orientation
      return KeyPadsLandscape
    }
  }
}
