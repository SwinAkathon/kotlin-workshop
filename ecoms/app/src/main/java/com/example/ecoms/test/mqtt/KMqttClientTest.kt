package com.example.ecoms.test.mqtt

import com.example.ecoms.common.mqtt.KMqttClient

fun main() {
  val client = KMqttClient()

  // use the same broker URL (especially the "ws://" part)
  // as configured on the MQTT web client.
  client.connect("ws://localhost:9001", "student", "student")

  client.subscribe(arrayOf(
    "ihome/feeds/temperature",
    "ihome/feeds/humidity",
    "ihome/feeds/light",
    "ihome/feeds/lamp1",
    "ihome/feeds/lamp2",
    "ihome/feeds/lamp3",
  ))

  try {
    client.waitLoop()
  } catch (e: KMqttClient.ByeException) {
    println(e.message)
    client.disconnect()
  }

}
