package com.example.ecoms.modules.iot

import java.sql.Date
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object Iotkit {
  val dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy")
  val timeFormat = DateTimeFormatter.ofPattern("hh:mm:ss")

  fun python2KotlinTimestamp(pyTimeStamp: Double): Timestamp {
    val milliseconds = (pyTimeStamp * 1000).toLong()
    return Timestamp(milliseconds)
  }

  fun toDate(timeStamp: Timestamp): LocalDateTime {
    //Date(timeStamp.time)
    return timeStamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
  }

  fun toFileName(topic: String): String {
    return topic.replace("/", "_", true)
  }
}