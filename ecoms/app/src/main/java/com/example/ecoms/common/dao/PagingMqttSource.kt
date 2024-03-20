package com.example.ecoms.common.dao

import androidx.paging.PagingState
import com.example.ecoms.common.mqtt.KMqttClient
import com.example.ecoms.modules.iot.Iotkit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage
import java.io.File
import java.io.FileInputStream
import java.io.FileWriter
import java.io.IOException
import java.io.PrintWriter

/**
 * @overview
 *  A generic paginated object loader for data stream.
 *  As messages are arrived asynchronously, they are stored into
 *  a CSV file (located in <tt>storagePath</tt>). Then, upon the UI's request,
 *  data from this file are loaded, one page at a time. This data loading
 *  is performed by a PagingFileSource instance.
 *
 * @requires storagePath is a valid path (in the src/main/assets directory)
 */
open class PagingMqttSource<T: Any> (
  private val url: String,
  private val user: String,
  private val password: String,
  private val storagePath: String,
  private val feedTopics: Array<String>,
  private val objectFileProc: ObjectFileProc<T>,
  private val clientOnAThread: Boolean = true
) : DataSource<T>() {

  private lateinit var dStorage: File
  private val writers = mutableMapOf<File, PrintWriter?>()

  private lateinit var dataSourceListener: (count: Int) -> Unit

  protected lateinit var client: KMqttClient

  private lateinit var fileSource: PagingDynamicFileSource<T>

  private var objCount: Int = 0;

  private val LTAG = PagingMqttSource::class.simpleName

  /**
   * An MqttCallback used for #client to inform this when message is available.
   */
  private val mqttCallBack: MqttCallback by lazy {
    object : MqttCallback {
      override fun connectionLost(cause: Throwable?) {
        log("Connection to MQTT broker lost!")
      }

      override fun messageArrived(topic: String?, message: MqttMessage?) {
        log("Message received: $message on topic: $topic")
        topic?.let {
          message?.let {
            saveData(topic, message.toString())
          }
        }
      }

      override fun deliveryComplete(token: IMqttDeliveryToken?) {
        // todo: handle publish event response
        log("Message delivered")
      }
    }
  } // end Callback

  init {
    if (clientOnAThread) {
      val scope = CoroutineScope(Dispatchers.IO)
      scope.launch {
        initClient()
      }
    } else {
      initClient()
    }
  }

  private fun initClient() {
    // initialise Mqtt client
    try {
      client = KMqttClient()
      client.connect(url, user, password, mqttCallBack)
    } catch (e: Exception) {
      throw MqttException(MqttException.REASON_CODE_CLIENT_EXCEPTION.toInt(), e)
    }

    // initialise file reader
    // todo: more than one topics?
    val topic = feedTopics[0]
    val fileName = Iotkit.toFileName(topic)
    val file = getFilePath(fileName)
    val fileIs = FileInputStream(file)
    fileSource = PagingDynamicFileSource(fileIs, objectFileProc)
  }

  /**
   * @requires
   *  message: contains the data value (e.g. "100")
   *  topic: dash-separated string, the last element of which is the domain attribute
   *    (e.g. topic = `ihome/feeds/temperature` => attribute = `temperature`)
   */
  private fun saveData(topic: String, message: String) {
    // store message into CSV file specified by storagePath
    // file name = topic after replacing all separator chars by "_"
    // places the message data into the CSV file named after the attribute (e.g. temperature.csv)
    // CSV record format: timestamp, value
    val fileName = Iotkit.toFileName(topic)
    val csvRecord = Message2CSVParser.parse(message)

    try {
      val file = getFilePath(fileName)
      val writer = getWriterInstance(file)
      writer.println(csvRecord)
      writer.flush()
      log("...written to $file")
    } catch (e: IOException) {
      log("Error: ${e.message}")
    }
  }

  private fun getFilePath(fileName: String): File {
    if (!storagePath.isEmpty()) {
      if (!::dStorage.isInitialized) {
        dStorage = File(storagePath)
        if (!dStorage.exists()) dStorage.mkdirs()
      }

      var theFile: File? = null
      for ((file, writer) in writers) {
        if (file.name.equals(fileName)) {
          theFile = file
          break
        }
      }

      if (theFile == null) {
        theFile = File(storagePath + File.separator + fileName).also {
          if (!it.exists()) it.createNewFile()
          writers[it] = null
        }
      }

      return theFile
    } else {
      return File(fileName)
    }
  }

  private fun getWriterInstance(file: File): PrintWriter {
    var writer = writers[file]
    if (writer == null) {
      writer = PrintWriter(FileWriter(file, true))
      writers[file] = writer
    }

    return writer
  }

  /**
   * @requires
   *  [client] has been successfully initialised by [init]
   *
   * @effects
   *  starts subscribing to [feedTopics] and processing the responses received with [mqttCallBack].
   */
  open fun listen() {
    client.subscribe(feedTopics)
  }

  /**
   * @effects
   *  incrementally read objects from the file specified in #storagePath, using #objectFileProc to
   *  convert each line to object and return a LoadResult.
   *
   *  The objects are read one page at a time, with the page size is specified in #params.loadSize.
   */
  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
    // load objects for the current page
    // delegate to fileSource
    return fileSource.load(params)
  }

  override fun getRefreshKey(state: PagingState<Int, T>): Int? {
    return fileSource.getRefreshKey(state)
  }

  override fun registerDataSourceListener(dataSourceListener: (count: Int) -> Unit) {
    this.dataSourceListener = dataSourceListener
    fileSource.registerDataSourceListener(dataSourceListener)
  }

  fun close() {
    client?.disconnect()
    if (::fileSource.isInitialized) fileSource.close()

    writers.forEach() { (path, writer) ->
      writer?.close()
    }
  }

  protected fun log(mesg: String?) {
    //Log.i(LTAG, mesg)
    println(mesg)
  }
}

object Message2CSVParser {
  /**
   * @effects
   *  returns the original CSV parsed record from message
   */
  fun parse(message: String): String {
    // removes the brackets (if any)
    if (message.startsWith("(")) {
      return message.removeSurrounding("(", ")")
    } else {
      return message
    }
  }
}
