package com.poc.kafkaproducer.sendNforget

import com.poc.kafkaproducer.config.{KafkaConnectionContants, KafkaProducerConfig}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord, RecordMetadata}

import java.util.Properties
import java.util.concurrent.Future

object SendAndForgetMessageProducer extends App {

  val producerConfig = KafkaProducerConfig("/Users/anujmehra/git/kafka-producer/src/main/resources/application.conf")
  println(producerConfig.bootstrapServers)

  val kafkaProps = new Properties
  kafkaProps.put(KafkaConnectionContants.BootstrapServers, producerConfig.bootstrapServers)
  kafkaProps.put(KafkaConnectionContants.KeySerializer, producerConfig.jsonMsgKeySerializer)
  kafkaProps.put(KafkaConnectionContants.ValueSerializer, producerConfig.jsonMsgValueSerializer)

  val topicName = producerConfig.jsonMsgTopicName
  val kafkaProducer = new KafkaProducer[String,String](kafkaProps)

  val sendAndForgetMessageProducer = new SendAndForgetMessageProducer
  sendAndForgetMessageProducer.produceMessages(kafkaProducer, topicName)

}

class SendAndForgetMessageProducer {

  def produceMessages: (KafkaProducer[String,String], String) => Unit
  = (kafkaProducer: KafkaProducer[String,String], topicName: String) => {

    println("-----method entry: produceMessages----")

    val record: ProducerRecord[String, String] = new ProducerRecord[String,String](topicName, "key2", "{f1:value2}")
    val futureResp: Future[RecordMetadata] = kafkaProducer.send(record)
    kafkaProducer.flush()
    println("-----method exit: produceMessages----")
    kafkaProducer.close()

    /*val recordMetadata: RecordMetadata = futureResp.get()
    println("offset ---> " + recordMetadata.offset())
    println("partition ---> " + recordMetadata.partition())
    println("topic ---> " + recordMetadata.topic())*/
  }
}
