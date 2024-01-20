package com.example.calculator.model

import kotlin.math.floor
import kotlin.math.sqrt

class BasicCalculator(var resultStr: String = "", var result: Double = 0.0) {
  private var currInput = ""
  private var prevInput = ""
  private var operation: String? = null

  fun input(input: String) {
    val num = input.toDoubleOrNull()

    if (input == "." || num != null) {
      // a number input
      currInput += input
      resultStr += input
    } else {
      if (input in KeysNotYetSupported) {
        // todo: Not yet supported
        // do nothing for now
        throw NotImplementedError("Key $input not yet implemented")
      } else {
        resultStr += input
        when (input) {
          "=" -> {  // compute result
            result = calculate()
            resultStr += if (isDoubleAnInteger(result)) result.toInt() else result
            // reset for next calculation
            prevInput = result.toString()
            //currInput = ""
            currInput = prevInput
          }
          "AC" -> {
            // clear
            clear()
          }
          else -> {
            // operator
            operation = input
            if (!currInput.isEmpty()) {
              prevInput = currInput
              currInput = ""
            }
          }
        }
      }
    }
  }

  fun setInput(input: String) {
    currInput = input
  }

  fun calculate(): Double {
    val res = when (operation) {
      "+" -> parseInput(prevInput) + parseInput(currInput)
      "-" -> parseInput(prevInput) - parseInput(currInput)
      mul -> parseInput(prevInput) * parseInput(currInput)
      div -> parseInput(prevInput) / parseInput(currInput)
      sqrt -> sqrt(parseInput(prevInput))
      rem -> parseInput(prevInput) % parseInput(currInput)
      else -> currInput.toDoubleOrNull() ?: 0.0
    }

    return res;
  }

  private fun parseInput(input: String): Double {
    return (input.toDoubleOrNull() ?: 0.0)
  }

  private fun isDoubleAnInteger(value: Double): Boolean {
    return value == floor(value) && !value.isInfinite() && !value.isNaN()
  }

  fun clear() {
    prevInput = ""
    currInput = ""
    resultStr = ""
    result = 0.0
  }
}