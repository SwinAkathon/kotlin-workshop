package com.example.ecoms.test.mqtt

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileReader
import java.io.RandomAccessFile
import kotlin.concurrent.thread
import kotlin.concurrent.thread

/**
 * Solution 2: using RandomAccessFile
 */
fun tailFile(filePath: String) {
  val file = File(filePath)
  thread {
    val randomAccessFile = RandomAccessFile(file, "r")
    var filePointer = 0L

    while (true) {
      try {
        val fileLength = file.length()
        if (fileLength < filePointer) {
          // If the file is smaller than the pointer, it might have been truncated or rotated
          println("File was reset or rotated")
          randomAccessFile.seek(0) // Start from the beginning of the file
          filePointer = 0
        } else if (fileLength > filePointer) {
          // There is new data to read
          randomAccessFile.seek(filePointer)
          var line: String?
          while (randomAccessFile.readLine().also { line = it } != null) {
            println(line)
          }
          filePointer = randomAccessFile.filePointer
        }
        // Wait before checking the file again
        Thread.sleep(1000)
      } catch (e: Exception) {
        println("An error occurred: ${e.message}")
        break
      }
    }
    randomAccessFile.close()
  }
}

/**
 * solution 1: using BufferedReader
 */
fun tailFile1(filePath: String) {
  val file = File(filePath)
  val fileIs = FileInputStream(file)

  CoroutineScope(Dispatchers.IO).launch {
    fileIs.bufferedReader().use { reader ->
    //BufferedReader(FileReader(file)).use { reader ->
      var line: String?
      while (true) {
        line = reader.readLine()
        if (line == null) {
          // No new lines, take a short break before checking again
          Thread.sleep(1000)  // Sleep for 1 second
        } else {
          println(line)  // Process the line
        }
      }
    }
  }
}

fun main() {
  val filePath = "data/ihome_feeds_humidity" // Replace with your actual file path
  tailFile(filePath)
}

