package com.example.ecoms.common.mqtt

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence

/**
 * @overview Provides a wrapper for MqttClient that supports the basic task flow,
 * including connect, publish, subscribe and disconnect.
 */
class KMqttClient {
  private lateinit var client: MqttClient

  private val defaultCallBack: MqttCallback by lazy {
    object : MqttCallback {
      override fun connectionLost(cause: Throwable?) {
        println("Connection to MQTT broker lost!")
      }

      override fun messageArrived(topic: String?, message: MqttMessage?) {
        println("Message received: $message on topic: $topic")
      }

      override fun deliveryComplete(token: IMqttDeliveryToken?) {
        println("Message delivered")
      }
    }
  }

  fun connect(url: String = "ws://localhost:9001", user: String, pwd: String,
              callback: MqttCallback = defaultCallBack) {
    val clientId = MqttClient.generateClientId()

    // use memory-based persistence to avoid creating temporary session folder
    val persistence = MemoryPersistence()

    client = MqttClient(url, clientId, persistence)

    val connOpts = MqttConnectOptions().apply {
      isCleanSession = true  // Clean session on connect
      userName = user  // Set your MQTT broker username here
      password = pwd.toCharArray()  // Set your MQTT broker password here
    }

    client.connect(connOpts)  // Connect to the MQTT broker with authentication

    client.setCallback(callback)
  }

  fun subscribe(topic: String) {
    client.subscribe(topic)
  }

  fun subscribe(topics: Array<String>) {
    topics.forEach() { topic ->
      client.subscribe(topic)
    }
  }

  fun unsubscribe(topics: Array<String>) {
    client.unsubscribe(topics)
  }

  fun publish(topic: String, msg: String) {
    // Publish messages to topics
    val message1 = MqttMessage(msg.toByteArray())
    client.publish(topic, message1)
  }

  fun disconnect() {
    client.disconnect()
    client.close()
  }

  fun waitLoop() {
    println("Enter 'q' to quit:")
    while(true) {
      Thread.sleep(1000)
      readlnOrNull()?.let { input ->
        if (input.equals("q", true)) {
          throw ByeException("Bye")
        }
      }
    }
  }

  /**
   * @overview represents a normal termination of this, when user typed 'q' in the
   * waitLoop().
   */
  class ByeException(message: String) : RuntimeException(message)
}