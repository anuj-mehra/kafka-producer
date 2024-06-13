## Start Kafka
cd /users/anujmehra/apps/kafka_2.13-2.7.0/bin/
./zookeeper-server-start.sh   /usersanujmehra/apps/kafka_2.13-2.7.0/config/zookeeper.properties
./kafka-server-start.sh  /users/anujmehra/apps/kafka_2.13-2.7.0/config/server.properties
./kafka-topics.sh --create --replication-factor 1 --partitions 4  --topic topic-1 --zookeeper  localhost:2181
./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic topic-1 --from-beginning

## Better to use Confluent Kafka
Install confluent Kafka;
https://www.youtube.com/watch?v=5x5GnBhyTMI

## Start confluent cluster;
confluent local services start

## Stop confluent cluster;
confluent local services stop

## Create a Topic
kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic users

## Start Confluent Kafka Producer on shell
kafka-console-producer --topic users --broker-list localhost:9092

## Start Confluent Kafka Consumer on shell
kafka-console-consumer --bootstrap-server localhost:9092 --topic users

## Add schema for "value" in the Confluent topic ----> we can have schemas for both "key" and "value"
kafka-json-schema-console-producer --broker-list localhost:9092 --topic users --property value.schema='{"type":"object","properties":{"f1":{"type":"string"}}}'

## Enable schema validation on the topic;
kafka-configs --bootstrap-server localhost:9092 --alter --entity-type topics --entity-name users --add-config confluent.value.schema.validation=true

### Helpful link;
https://docs.confluent.io/platform/current/schema-registry/serdes-develop/index.html#sr-schemas-subject-name-strategy

## List created topics



## Important topics;

1. Send And Forget
2. Sync API
3. ASync API
4. Transaction Management
5. Dead Letter Topic
6. Custom Partitioner
7. batch.size + linger.ms --> in producer
8. At-most once vs at-least once vs exactly once


## Helpful URL's

https://betterprogramming.pub/thorough-introduction-to-apache-kafka-6fbf2989bbc1

https://betterprogramming.pub/kafka-acks-explained-c0515b3b707e
