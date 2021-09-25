package com.poc.kafkaproducer.config

import java.io.File
import com.typesafe.config.ConfigFactory

class KafkaProducerConfig (applicationConfFile: String) extends Serializable {

  val config = ConfigFactory.parseFile(new File(applicationConfFile)).resolve

  /*schema.registry.url = ""
  ssl.truststore.location=""
  ssl.truststore.password=""
  service.discovery.url = ""*/
  /* security.protocol= ""
   sasl.mechanism = ""
   kafka.username=""
   kafka.password=""*/

  lazy val bootstrapServers= config.getString("producer_config.bootstrap_servers")
  lazy val jsonMsgConsumerGroupId = config.getString("producer_config.json_message.consumer_group_id")
  lazy val jsonMsgTopicName = config.getString("producer_config.json_message.topic_name")
  lazy val jsonMsgKeySerializer = config.getString("producer_config.json_message.key_serializer")
  lazy val jsonMsgValueSerializer = config.getString("producer_config.json_message.value_serializer")

  lazy val avroMsgConsumerGroupId = config.getString("producer_config.avro_message.consumer_group_id")
  lazy val avroMsgTopicName = config.getString("producer_config.avro_message.topic_name")
  lazy val avroMsgKeySerializer = config.getString("producer_config.avro_message.key_serializer")
  lazy val avroMsgValueSerializer = config.getString("producer_config.avro_message.value_serializer")

}

object KafkaProducerConfig {
  def apply(applicationConfFile: String) = new KafkaProducerConfig(applicationConfFile)
}
