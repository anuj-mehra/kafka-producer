## Start Kafka
cd /users/anujmehra/apps/kafka_2.13-2.7.0/bin/
./zookeeper-server-start.sh   /usersanujmehra/apps/kafka_2.13-2.7.0/config/zookeeper.properties
./kafka-server-start.sh  /users/anujmehra/apps/kafka_2.13-2.7.0/config/server.properties
./kafka-topics.sh --create --replication-factor 1 --partitions 4  --topic topic-1 --zookeeper  localhost:2181
./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic topic-1 --from-beginning

## Better to use Confluent Kafka
Install confluent Kafka;
https://www.youtube.com/watch?v=5x5GnBhyTMI

Start confluent cluster;
confluent local services start

## Start Confluent Kafka Producer on shell
kafka-console-producer --topic topic-1 --broker-list localhost:9092

## List created topics



##Important topics;
1. Send And Forget
2. Sync API
3. ASync API
4. Transaction Management
5. Dead Letter Topic
6. Custom Partitioner


