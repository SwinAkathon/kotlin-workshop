package com.example.ecoms.common.dao

class ObjectFileProc<T: Any> (
  private val fieldDelimiter: String = ",",
  private val objectProducer: (tokens: List<String>) -> T
) {
  fun toObject(line: String): T {
    val tokens = line.split(fieldDelimiter)
    return objectProducer(tokens)
  }
}