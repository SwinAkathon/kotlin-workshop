package com.example.calculator

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.platform.LocalContext
import com.example.calculator.model.BasicCalculator
import com.example.calculator.model.CalcModel
import com.example.calculator.model.KeyPadsLandscape
import com.example.calculator.model.KeyPadsPortrait
import com.example.calculator.view.CalculatorScreen
import com.example.calculator.view.theme.CalculatorTheme

class CalculatorAct : ComponentActivity() {
  // kep a MutableState of the keyPads to present, based on the Device orientation
  // which keyPads structure to use is defined in the calculator's model and is retrieved
  // at run time by the function onConfigurationChanged
  private lateinit var calcModel: CalcModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // initialise a calculator object
    val calc = BasicCalculator()

    setContent {
      val context = LocalContext.current;

      CalculatorTheme {
        // initialise the calculator screen

        // initialise the view model
        calcModel = viewModels<CalcModel>().value
        calcModel.initState(getKeyPadsConfiguration(resources.configuration),calc.resultStr)

        // now the screen
        CalculatorScreen(context, calc, calcModel)
      }
    }
  }

  /**
   * Invoked when device has rotated
   * @requires property <tt>android:configChanges="orientation|screenSize"</tt> is added to this activity configuration
   * in the AndroidManifest file
   */
  override fun onConfigurationChanged(newConfig: Configuration) {
    super.onConfigurationChanged(newConfig)
    // determines the keyPads map suitable for the orientation
    // update keyPadsFlex to cause Recomposition of the screen
    calcModel.onConfigurationChanged(getKeyPadsConfiguration(newConfig))
  }

  /**
   * @effects return either KeyPadsPortrait or KeyPadsLandscape depending on the screen orientation
   */
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
