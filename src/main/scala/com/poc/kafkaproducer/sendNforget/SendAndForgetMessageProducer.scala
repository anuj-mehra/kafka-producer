package com.poc.kafkaproducer.sendNforget

import com.poc.kafkaproducer.config.{KafkaConnectionContants, KafkaProducerConfig}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

import java.util.Properties

object SendAndForgetMessageProducer extends App {

  val producerConfig = KafkaProducerConfig("/Users/anujmehra/git/kafka-producer/src/main/resources/application.conf")
  println(producerConfig.bootstrapServers)

  val properties = new Properties
  properties.put(KafkaConnectionContants.BootstrapServers, producerConfig.bootstrapServers)
  properties.put(KafkaConnectionContants.KeyDeserializer, producerConfig.jsonMsgKeySerializer)
  properties.put(KafkaConnectionContants.ValueDeserializer, producerConfig.jsonMsgValueSerializer)

  val topicName = producerConfig.jsonMsgTopicName
  val kafkaProducer = new KafkaProducer[String,String](properties)

  val producer = new SendAndForgetMessageProducer
  producer.produceMessages(kafkaProducer, topicName)
}

class SendAndForgetMessageProducer {

  def produceMessages: (KafkaProducer[String,String], String) => Unit
  = (kafkaProducer: KafkaProducer[String,String], topicName: String) => {

    println("-----method entry: produceMessages----")

    val record: ProducerRecord[String, String] = new ProducerRecord[String,String](topicName, "key1", "value1")
    kafkaProducer.send(record)

    println("-----method exit: produceMessages----")
  }
}
