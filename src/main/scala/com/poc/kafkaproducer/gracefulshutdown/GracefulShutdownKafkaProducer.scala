package com.poc.kafkaproducer.gracefulshutdown

import com.poc.kafkaproducer.config.{KafkaConnectionContants, KafkaProducerConfig}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord, RecordMetadata}

import java.util.Properties
import java.util.concurrent.Future
import scala.util.{Failure, Success, Try}

object GracefulShutdownKafkaProducer extends App {

  val producerConfig = KafkaProducerConfig("/Users/anujmehra/git/kafka-producer/src/main/resources/application.conf")
  println(producerConfig.bootstrapServers)

  val kafkaProps = new Properties
  kafkaProps.put(KafkaConnectionContants.BootstrapServers, producerConfig.bootstrapServers)
  kafkaProps.put(KafkaConnectionContants.KeySerializer, producerConfig.jsonMsgKeySerializer)
  kafkaProps.put(KafkaConnectionContants.ValueSerializer, producerConfig.jsonMsgValueSerializer)
  kafkaProps.put(KafkaConnectionContants.Acknowledgement,"all")
 /* kafkaProps.put(KafkaConnectionContants.BatchSize,"")
  kafkaProps.put(KafkaConnectionContants.LingerMs,"")*/

  val topicName = producerConfig.jsonMsgTopicName
  val kafkaProducer = new KafkaProducer[String,String](kafkaProps)

  val sendAndForgetMessageProducer = new GracefulShutdownKafkaProducer
  sendAndForgetMessageProducer.produceMessages(kafkaProducer, topicName)

}

class GracefulShutdownKafkaProducer {

  def produceMessages: (KafkaProducer[String,String], String) => Unit
  = (kafkaProducer: KafkaProducer[String,String], topicName: String) => {

    var i=0;
    try{
      while(true){
        val message: ProducerRecord[String, String] =
          new ProducerRecord[String,String](topicName, "key2", s"s{name:anuj-${i}")
        i=i+1;

        sendMessage(message, kafkaProducer) match {
          case Success(x) => // do nothing
          case Failure(e) =>
            println(e.printStackTrace())
        }
      }
    }finally{
      println("----calling flush----")
      kafkaProducer.flush()
      kafkaProducer.close()
    }

  }

  private def sendMessage(message: ProducerRecord[String, String],
                          kafkaProducer: KafkaProducer[String,String]): Try[Long] = {

    try{
      val futureResp: Future[RecordMetadata] = kafkaProducer.send(message)
      val recordMetadata = futureResp.get()
      println("offset--->" + recordMetadata.offset())
      println("partition--->" + recordMetadata.partition())
      println("topic--->" + recordMetadata.topic())

      Success(recordMetadata.offset())
    }catch{
      case e: Exception => Failure(e)
    }
  }

}
