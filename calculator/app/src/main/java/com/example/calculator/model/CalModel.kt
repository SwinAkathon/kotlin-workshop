package com.example.calculator.model

/**
 * View components configuration for the Calculator
 */
val mul = "x"
val div = "\u00f7"
val sqrt = "\u221Ax"
val rem = "%"

val KeyPads = mapOf<Int, Array<String>>(
  1 to arrayOf("MC", "M+", "M-", "MR"),
  2 to arrayOf("AC", sqrt, rem, div),
  3 to arrayOf("7", "8", "9", mul),
  4 to arrayOf("4", "5", "6", "-"),
  5 to arrayOf("1", "2", "3", "+"),
  6 to arrayOf("0", ".", "+/-", "=")
)

val KeysNotYetSupported = arrayOf("MC", "M+", "M-", "MR", "+/-")
