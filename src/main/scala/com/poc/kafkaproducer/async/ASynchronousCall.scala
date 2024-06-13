package com.poc.kafkaproducer.async

import com.poc.kafkaproducer.config.{KafkaConnectionContants, KafkaProducerConfig}
import org.apache.kafka.clients.producer.{Callback, KafkaProducer, ProducerRecord, RecordMetadata}

import java.io.Serializable
import java.util.Properties

object ASynchronousCall extends App{

  val producerConfig = KafkaProducerConfig("/Users/anujmehra/git/kafka-producer/src/main/resources/application.conf")
  println(producerConfig.bootstrapServers)

  val kafkaProps = new Properties
  kafkaProps.put(KafkaConnectionContants.BootstrapServers, producerConfig.bootstrapServers)
  kafkaProps.put(KafkaConnectionContants.KeySerializer, producerConfig.jsonMsgKeySerializer)
  kafkaProps.put(KafkaConnectionContants.ValueSerializer, producerConfig.jsonMsgValueSerializer)

  /* acks=0 ==> With a value of 0, the producer won’t even wait for a response from the broker.
     It immediately considers the write successful the moment the record is sent out.*/
  /* acks=1 ==> With a setting of 1, the producer will consider the write successful when the
     leader receives the record. The leader broker will know to immediately respond the moment
     it receives the record and not wait any longer.
   */
  // acks=all ==> When set to all, the producer will consider the write successful when all of the in-sync replicas receive the record.
  kafkaProps.put(KafkaConnectionContants.Acknowledgement,"all")

  //The total bytes of memory the producer can use to buffer records waiting to be sent to the server.
  //Default value ==> 33554432
  kafkaProps.put(KafkaConnectionContants.BufferMemory,"33554432")

  //The producer will attempt to batch records together into fewer requests whenever multiple records are being sent to the same partition.
  //This helps performance on both the client and the server. This configuration controls the default batch size in bytes.
  //No attempt will be made to batch records larger than this size.
  //Requests sent to brokers will contain multiple batches, one for each partition with data available to be sent.
  // Default value == 16384 bytes
  kafkaProps.put(KafkaConnectionContants.BatchSize,"16384")

  //This setting defaults to 0 (i.e. no delay). Setting linger.ms=5,
  // for example, would have the effect of reducing the number of requests sent but would add up to 5ms of latency to records sent in the absence of load.
  kafkaProps.put(KafkaConnectionContants.LingerMs,"5")

  val topicName = producerConfig.jsonMsgTopicName
  val kafkaProducer = new KafkaProducer[String,String](kafkaProps)

  val asyncProducer = new ASynchronousCall
  asyncProducer producerMessages(kafkaProducer, topicName)
}

class ASynchronousCall extends Serializable {

  def producerMessages: (KafkaProducer[String,String], String) => Unit
  = (kafkaProducer: KafkaProducer[String,String], topicName: String) => {

    try{
      val counter = 100

      for(i <- 0 to counter){
        val key = i.toString
        val value = s"s{name:anuj-${i}"
        val message: ProducerRecord[String, String] = new ProducerRecord[String,String](topicName, value)
        kafkaProducer.send(message, new DemoUserCallback(key, value))
      }

    }catch{
      case e: Exception =>
        println("----- exception occured : producer --> " + e.printStackTrace())
    }finally{
      println("----calling flush----")
      kafkaProducer.flush()
      kafkaProducer.close()
    }

  }
}

class DemoUserCallback(key: String, value: String) extends Callback{

  override def onCompletion(recordMetadata: RecordMetadata, e: Exception): Unit = {
    val ex = Option(e)

    ex.isDefined match {
      case true =>
        e.printStackTrace()
        println(s"---not able to send message for key = ${key} and value = ${value}")

      case false =>
        println("offset--->" + recordMetadata.offset())
        println("partition--->" + recordMetadata.partition())
        println("topic--->" + recordMetadata.topic())
    }
  }

}