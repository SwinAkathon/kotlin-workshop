package com.example.ecoms.common.dao

import com.example.ecoms.common.mqtt.KMqttClient

class PagingMqttSourceViaConsole<T: Any>(
 private val url: String,
 private val user: String,
 private val password: String,
 private val storagePath: String,
 private val feedTopics: Array<String>,
 private val objectFileProc: ObjectFileProc<T>,
 private val clientOnAThread: Boolean = false
): PagingMqttSource<T>(url, user, password, storagePath, feedTopics, objectFileProc, clientOnAThread) {
  override fun listen() {
    super.listen()

    // console-based interface -> wait and provide a prompt to quit
    try {
      client.waitLoop()
    } catch (e: KMqttClient.ByeException) {
      close()
      log(e.message)
    }
  }
}