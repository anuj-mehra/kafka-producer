package com.poc.kafkaproducer.sync

import com.poc.kafkaproducer.config.{KafkaConnectionContants, KafkaProducerConfig}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord, RecordMetadata}

import java.io.Serializable
import java.util.Properties
import java.util.concurrent.Future

object SynchronousCall extends App with Serializable{

  val producerConfig = KafkaProducerConfig("/Users/anujmehra/git/kafka-producer/src/main/resources/application.conf")
  println(producerConfig.bootstrapServers)

  val kafkaProps = new Properties
  kafkaProps.put(KafkaConnectionContants.BootstrapServers, producerConfig.bootstrapServers)
  kafkaProps.put(KafkaConnectionContants.KeySerializer, producerConfig.jsonMsgKeySerializer)
  kafkaProps.put(KafkaConnectionContants.ValueSerializer, producerConfig.jsonMsgValueSerializer)
  kafkaProps.put(KafkaConnectionContants.Acknowledgement,"all")
  kafkaProps.put(KafkaConnectionContants.BatchSize,"")
  kafkaProps.put(KafkaConnectionContants.LingerMs,"")

  val topicName = producerConfig.jsonMsgTopicName
  val kafkaProducer = new KafkaProducer[String,String](kafkaProps)

  val obj = new SynchronousCall
  obj.producerMessages(kafkaProducer, topicName)
}

class SynchronousCall extends Serializable {

  def producerMessages: (KafkaProducer[String,String], String) => Unit
  = (kafkaProducer: KafkaProducer[String,String], topicName: String) => {

    try{
      val counter = 100

      for(i <- 0 to counter){
        val message: ProducerRecord[String, String] = new ProducerRecord[String,String](topicName, "key2", s"s{name:anuj-${i}")

        val futureResp: Future[RecordMetadata] = kafkaProducer.send(message)
        val recordMetadata = futureResp.get()
        println("offset--->" + recordMetadata.offset())
        println("partition--->" + recordMetadata.partition())
        println("topic--->" + recordMetadata.topic())
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
