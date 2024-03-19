package com.example.ecoms.test.mqtt

import com.example.ecoms.common.dao.ObjectFileProc
import com.example.ecoms.common.dao.PagingMqttSource
import com.example.ecoms.common.dao.PagingMqttSourceViaConsole

fun main() {
  val storagePath = "data"
  val url = "ws://localhost:9001"
  val user = "student"
  val password = "student"
  val topics = arrayOf(
    "ihome/feeds/temperature",
    "ihome/feeds/humidity",
    "ihome/feeds/light",
    "ihome/feeds/lamp1",
    "ihome/feeds/lamp2",
    "ihome/feeds/lamp3",
  )
  val objFileProc = ObjectFileProc<String>(
    objectProducer =  { tokens ->
      tokens.toString()
    }
  )
  val clientOnAThread = false

  val source = PagingMqttSourceViaConsole(url, user, password, storagePath, topics, objFileProc, clientOnAThread)
  source.listen()
}