package com.poc.kafkaproducer.config

object KafkaConnectionContants extends Enumeration {

  type Main = Value

  // Assigning values
  val BootstrapServers = Value("bootstrap.servers").toString
  val KeySerializer = Value("key.serializer").toString
  val ValueSerializer = Value("value.serializer").toString
}
