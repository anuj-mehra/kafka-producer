package com.poc.kafkaproducer2

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord, RecordMetadata}

import java.util.Properties

object SyncheronousKafkaProducer extends App{

  val producerConfig = KafkaProducerConfig("/Users/anujmehra/git/kafka-producer/src/main/resources/application2.conf")
  println(producerConfig.bootstrapServers)

  val kafkaProps = new Properties
  kafkaProps.put(KafkaConnectionContants.BootstrapServers, producerConfig.bootstrapServers)
  kafkaProps.put(KafkaConnectionContants.KeySerializer, producerConfig.jsonMsgKeySerializer)
  kafkaProps.put(KafkaConnectionContants.ValueSerializer, producerConfig.jsonMsgValueSerializer)

  val topicName = producerConfig.jsonMsgTopicName
  val kafkaProducer = new KafkaProducer[String,String](kafkaProps)

  val syncheronousKafkaProducer = new SyncheronousKafkaProducer
  syncheronousKafkaProducer.produceMessages(kafkaProducer, topicName)
}

class SyncheronousKafkaProducer extends Serializable {

  def produceMessages(kafkaProducer: KafkaProducer[String,String], topicName: String): Unit = {

    println("-----method entry: produceMessages----")

    val key = "key4"
    val value = "{f1:value4}"
    val record: ProducerRecord[String, String] = new ProducerRecord[String,String](topicName, key, value)
    val recordMetadata: RecordMetadata = kafkaProducer.send(record).get()

    kafkaProducer.flush()
    kafkaProducer.close()

    println("offset ---> " + recordMetadata.offset())
    println("partition ---> " + recordMetadata.partition())
    println("topic ---> " + recordMetadata.topic())

    println(s"---Message successfully delivered  for key=${key} at offset=${recordMetadata.offset()} " +
      s"of topic=${recordMetadata.topic()} and partition=${recordMetadata.partition()}")

    println("-----method exit: produceMessages----")
  }

}