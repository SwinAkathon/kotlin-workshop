package com.example.ecoms.modules.iot.humidity.dao

import com.example.ecoms.common.dao.ObjectFileProc
import com.example.ecoms.common.dao.PagingMqttSource
import com.example.ecoms.modules.iot.Iotkit
import com.example.ecoms.modules.iot.humidity.model.Humidity

class HumidityPagingSource (private val storagePath: String) :
  PagingMqttSource<Humidity>(
    // IMPORTANT: need to use the actual interface IP address when testing inside the emulator
    // (localhost: does not work with emulator)
  "ws://10.1.4.34:9001",
  "student",
  "student",
  storagePath,
  arrayOf("ihome/feeds/humidity"),
  ObjectFileProc<Humidity>(
    objectProducer =  { tokens ->
      Humidity(
        Iotkit.python2KotlinTimestamp(tokens[0].toDouble()),
        tokens[1].toInt())
    }),
  false
) {
    // empty
}