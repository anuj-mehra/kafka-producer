package com.poc.kafkaproducer.sendNforget

import com.poc.kafkaproducer.config.KafkaProducerConfig

import java.util.Properties

object SendAndForgetMessageProducer extends App {

  val producerConfig = KafkaProducerConfig("/Users/anujmehra/git/kafka-producer/src/main/resources/application.conf")

  /*println(producerConfig.bootstrapServers)

  val properties = new Properties
  properties.put(KafkaConnectionContants.BootstrapServers, producerConfig.bootstrapServers)

  val consumer = producerConfig(properties, producerConfig)
  consumer.process*/

}

class SendAndForgetMessageProducer {

}
