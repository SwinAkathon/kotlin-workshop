package com.example.calculator.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

/**
 * View components configuration for the Calculator
 */
val mul = "x"
val div = "\u00f7"
val sqrt = "\u221Ax"
val rem = "%"

/**
 * keypads configuration for Portrait-sized screen
 */
val KeyPadsPortrait = mapOf<Int, Array<String>>(
  1 to arrayOf("MC", "M+", "M-", "MR"),
  2 to arrayOf("AC", sqrt, rem, div),
  3 to arrayOf("7", "8", "9", mul),
  4 to arrayOf("4", "5", "6", "-"),
  5 to arrayOf("1", "2", "3", "+"),
  6 to arrayOf("0", ".", "+/-", "=")
)

/**
 * keypads configuration for Landscape-sized screen
 */
val KeyPadsLandscape = mapOf<Int, Array<String>>(
  1 to arrayOf("AC", "MC", "M+", "M-", "MR"),
  2 to arrayOf(sqrt, rem, div, mul, "-"),
  3 to arrayOf( "6", "7", "8", "9", "+"),
  4 to arrayOf("2", "3", "4", "5", "."),
  5 to arrayOf("0", "1", "+/-", "=" )
)

val KeysNotYetSupported = arrayOf("MC", "M+", "M-", "MR", "+/-")

/**
 * @overview the ViewModel that captures state of the Calculator
 */
class CalcModel(): ViewModel() {

  // wraps keyPads by MutableState, needed for recomposition of Composables
  var keyPads: MutableState<Map<Int, Array<String>>> = mutableStateOf(mapOf())

  // wraps calcResult by a MutableState to keep track of its changes
  val result: MutableState<String> = mutableStateOf("")

  fun initState(keyPads: Map<Int, Array<String>>, resultStr: String) {
    this.keyPads.value = keyPads
    result.value = resultStr
  }

  fun onConfigurationChanged(keyPads: Map<Int, Array<String>>) {
    // update keyPads, causing recomposition of the Composables
    this.keyPads.value = keyPads
  }
}