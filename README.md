## Apache Kafka Series - Learn Apache Kafka for Beginners v2 ##

* udemy course:
https://www.udemy.com/course/apache-kafka/learn/lecture/11566932#overview

* code in github:
https://github.com/windstef/kafka-beginners-course/find/master


> 29.Windows - Summary
In summary, for Windows
Download and Setup Java 8 JDK

Download the Kafka binaries from https://kafka.apache.org/downloads

Extract Kafka at the root of C:\

Setup Kafka bins in the Environment variables section by editing Path

Try Kafka commands using kafka-topics.bat (for example)

Edit Zookeeper & Kafka configs using NotePad++ https://notepad-plus-plus.org/download/

zookeeper.properties: dataDir=C:/kafka_2.12-2.0.0/data/zookeeper (yes the slashes are inversed)

server.properties: log.dirs=C:/kafka_2.12-2.0.0/data/kafka (yes the slashes are inversed)

Start Zookeeper in one command line: zookeeper-server-start.bat config\zookeeper.properties

Start Kafka in another command line: kafka-server-start.bat config\server.properties



> 32. Kafka Topics CLI
kafka-topics --zookeeper 127.0.0.1:2181 --topic first_topic --create --partitions 3 --replication-factor 1

kafka-topics --zookeeper 127.0.0.1:2181 --list

kafka-topics --zookeeper 127.0.0.1:2181 --topic first_topic --describe



> 33. Kafka Console Producer CLI

kafka-console-producer.bat --broker-list 127.0.0.1:9092 --topic first_topic

kafka-console-producer.bat --broker-list 127.0.0.1:9092 --topic first_topic --producer-property acks=all

PS C:\kafka_2.12-2.0.0> kafka-console-producer.bat --broker-list 127.0.0.1:9092 --topic new_topic
>this topic is not exist!
[2019-12-16 17:04:19,249] WARN [Producer clientId=console-producer] Error while fetching metadata with correlation id 1 : {new_topic=LEADER_NOT_AVAILABLE} (org.apache.kafka.clients.NetworkClient)
>another message, 2nd, in new_topic

kafka-console-producer.bat --broker-list 127.0.0.1:9092 --topic new_topic

kafka-topics --zookeeper 127.0.0.1:2181 --topic new_topic --describe


> 34. Kafka Console Consumer CLI
kafka-console-consumer --bootstrap-server 127.0.0.1:9092 --topic first_topic

kafka-console-consumer --bootstrap-server 127.0.0.1:9092 --topic first_topic --from-beginning


> 35. Kafka Consumers in Group
kafka-console-consumer --bootstrap-server 127.0.0.1:9092 --topic first_topic --group my-first-application
(x2)

kafka-console-consumer --bootstrap-server 127.0.0.1:9092 --topic first_topic --group my-second-application --from-begin
ning
(displays messages Only the 1st time)


> 36. Kafka Consumer Groups CLI

kafka-consumer-groups --bootstrap-server localhost:9092 --list

PS C:\kafka_2.12-2.0.0> kafka-consumer-groups --bootstrap-server localhost:9092 --describe --group my-second-application
Consumer group 'my-second-application' has no active members.

TOPIC           PARTITION  CURRENT-OFFSET  LOG-END-OFFSET  LAG             CONSUMER-ID     HOST            CLIENT-ID
first_topic     0          10              10              0               -               -               -
first_topic     2          9               9               0               -               -               -
first_topic     1          8               8               0               -               -               -


PS C:\kafka_2.12-2.0.0> kafka-consumer-groups --bootstrap-server localhost:9092 --describe --group my-first-application
Consumer group 'my-first-application' has no active members.

TOPIC           PARTITION  CURRENT-OFFSET  LOG-END-OFFSET  LAG             CONSUMER-ID     HOST            CLIENT-ID
first_topic     0          9               10              1               -               -               -
first_topic     2          7               9               2               -               -               -
first_topic     1          7               8               1               -               -               -

PS C:\kafka_2.12-2.0.0> kafka-console-consumer --bootstrap-server 127.0.0
.1:9092 --topic first_topic --group my-first-application



> 45.Java Producer
ProducerDemo.class

kafka-console-consumer --bootstrap-server 127.0.0.1:9092 --topic first_topic --group my-third-application

PS C:\kafka_2.12-2.0.0> kafka-consumer-groups --bootstrap-server localhost:9092 --describe --group my-third-application

TOPIC           PARTITION  CURRENT-OFFSET  LOG-END-OFFSET  LAG             CONSUMER-ID                                     HOST            CLIENT-ID
first_topic     0          26              26              0               consumer-1-6fc8508c-4e42-4956-ad1e-a3ac42aeac15 /10.0.75.1      consumer-1
first_topic     1          19              19              0               consumer-1-6fc8508c-4e42-4956-ad1e-a3ac42aeac15 /10.0.75.1      consumer-1
first_topic     2          27              27              0               consumer-1-6fc8508c-4e42-4956-ad1e-a3ac42aeac15 /10.0.75.1      consumer-1

> 46. Java Producer Callbacks
ProducerDemoWithCallBack.class


> 47. Java Producer with Keys
ProducerDemoKeys.class

Tip: "keys will go Always to the same partition
for a fixed number of partitions"



> 48. Java Consumer
ConsumerDemo.class


> 49. Java Consumer inside Consumer Group

kafka-consumer-groups --bootstrap-server localhost:9092 --group my-fourth-application --describe

- start a second consumer in parallel (rebalancing)
(Intellij: configuration: check option 'Allow parallel run'

- start a third consumer in parallel (rebalancing)

map: topic  - > partition
first_topic-0  ->  Partition: 0
first_topic-1  ->  Partition: 1
first_topic-2 ->  Partition: 2


A) first Consumer (first running)
-- logs:
[main] INFO org.apache.kafka.clients.consumer.internals.AbstractCoordinator - [Consumer clientId=consumer-1, groupId=my-fifth-application] Discovered group coordinator PC-HEM00018977.rce-group.local:9092 (id: 2147483647 rack: null)
[main] INFO org.apache.kafka.clients.consumer.internals.ConsumerCoordinator - [Consumer clientId=consumer-1, groupId=my-fifth-application] Revoking previously assigned partitions []
[main] INFO org.apache.kafka.clients.consumer.internals.AbstractCoordinator - [Consumer clientId=consumer-1, groupId=my-fifth-application] (Re-)joining group
[main] INFO org.apache.kafka.clients.consumer.internals.AbstractCoordinator - [Consumer clientId=consumer-1, groupId=my-fifth-application] Successfully joined group with generation 8
[main] INFO org.apache.kafka.clients.consumer.internals.ConsumerCoordinator - [Consumer clientId=consumer-1, groupId=my-fifth-application] Setting newly assigned partitions [first_topic-0, first_topic-1, first_topic-2]
[main] INFO org.apache.kafka.clients.consumer.internals.AbstractCoordinator - [Consumer clientId=consumer-1, groupId=my-fifth-application] Attempt to heartbeat failed since group is rebalancing
[main] INFO org.apache.kafka.clients.consumer.internals.ConsumerCoordinator - [Consumer clientId=consumer-1, groupId=my-fifth-application] Revoking previously assigned partitions [first_topic-0, first_topic-1, first_topic-2]
[main] INFO org.apache.kafka.clients.consumer.internals.AbstractCoordinator - [Consumer clientId=consumer-1, groupId=my-fifth-application] (Re-)joining group
[main] INFO org.apache.kafka.clients.consumer.internals.AbstractCoordinator - [Consumer clientId=consumer-1, groupId=my-fifth-application] Successfully joined group with generation 9
[main] INFO org.apache.kafka.clients.consumer.internals.ConsumerCoordinator - [Consumer clientId=consumer-1, groupId=my-fifth-application] Setting newly assigned partitions [first_topic-2]


- after running: ProducerDemoKeys [first_topic-2]
-- logs:
[main] INFO com.github.stefk.kafka.tutorial1.ConsumerDemoGroups - key: id_0, Value: Hello World 0
[main] INFO com.github.stefk.kafka.tutorial1.ConsumerDemoGroups - Partition: 1, Offset: 23
[main] INFO com.github.stefk.kafka.tutorial1.ConsumerDemoGroups - key: id_1, Value: Hello World 1
[main] INFO com.github.stefk.kafka.tutorial1.ConsumerDemoGroups - Partition: 0, Offset: 29
[main] INFO com.github.stefk.kafka.tutorial1.ConsumerDemoGroups - key: id_3, Value: Hello World 3
[main] INFO com.github.stefk.kafka.tutorial1.ConsumerDemoGroups - Partition: 0, Offset: 30
[main] INFO com.github.stefk.kafka.tutorial1.ConsumerDemoGroups - key: id_6, Value: Hello World 6
[main] INFO com.github.stefk.kafka.tutorial1.ConsumerDemoGroups - Partition: 0, Offset: 31
[main] INFO com.github.stefk.kafka.tutorial1.ConsumerDemoGroups - key: id_8, Value: Hello World 8
[main] INFO com.github.stefk.kafka.tutorial1.ConsumerDemoGroups - Partition: 1, Offset: 24



- after running second Consumer
-- logs:
[main] INFO org.apache.kafka.clients.consumer.internals.AbstractCoordinator - [Consumer clientId=consumer-1, groupId=my-fifth-application] Discovered group coordinator PC-HEM00018977.rce-group.local:9092 (id: 2147483647 rack: null)
[main] INFO org.apache.kafka.clients.consumer.internals.ConsumerCoordinator - [Consumer clientId=consumer-1, groupId=my-fifth-application] Revoking previously assigned partitions []
[main] INFO org.apache.kafka.clients.consumer.internals.AbstractCoordinator - [Consumer clientId=consumer-1, groupId=my-fifth-application] (Re-)joining group
[main] INFO org.apache.kafka.clients.consumer.internals.AbstractCoordinator - [Consumer clientId=consumer-1, groupId=my-fifth-application] Successfully joined group with generation 9
[main] INFO org.apache.kafka.clients.consumer.internals.ConsumerCoordinator - [Consumer clientId=consumer-1, groupId=my-fifth-application] Setting newly assigned partitions [first_topic-0, first_topic-1]


-- after running: ProducerDemoKeys [first_topic-0, first_topic-1]
-- logs:
[main] INFO com.github.stefk.kafka.tutorial1.ConsumerDemoGroups - key: id_2, Value: Hello World 2
[main] INFO com.github.stefk.kafka.tutorial1.ConsumerDemoGroups - Partition: 2, Offset: 32
[main] INFO com.github.stefk.kafka.tutorial1.ConsumerDemoGroups - key: id_4, Value: Hello World 4
[main] INFO com.github.stefk.kafka.tutorial1.ConsumerDemoGroups - Partition: 2, Offset: 33
[main] INFO com.github.stefk.kafka.tutorial1.ConsumerDemoGroups - key: id_5, Value: Hello World 5
[main] INFO com.github.stefk.kafka.tutorial1.ConsumerDemoGroups - Partition: 2, Offset: 34
[main] INFO com.github.stefk.kafka.tutorial1.ConsumerDemoGroups - key: id_7, Value: Hello World 7
[main] INFO com.github.stefk.kafka.tutorial1.ConsumerDemoGroups - Partition: 2, Offset: 35
[main] INFO com.github.stefk.kafka.tutorial1.ConsumerDemoGroups - key: id_9, Value: Hello World 9
[main] INFO com.github.stefk.kafka.tutorial1.ConsumerDemoGroups - Partition: 2, Offset: 36


-- after running third Consumer

--- Consumer A [first_topic-1]
[main] INFO org.apache.kafka.clients.consumer.internals.AbstractCoordinator - [Consumer clientId=consumer-1, groupId=my-fifth-application] Attempt to heartbeat failed since group is rebalancing
[main] INFO org.apache.kafka.clients.consumer.internals.ConsumerCoordinator - [Consumer clientId=consumer-1, groupId=my-fifth-application] Revoking previously assigned partitions [first_topic-2]
[main] INFO org.apache.kafka.clients.consumer.internals.AbstractCoordinator - [Consumer clientId=consumer-1, groupId=my-fifth-application] (Re-)joining group
[main] INFO org.apache.kafka.clients.consumer.internals.AbstractCoordinator - [Consumer clientId=consumer-1, groupId=my-fifth-application] Successfully joined group with generation 10
[main] INFO org.apache.kafka.clients.consumer.internals.ConsumerCoordinator - [Consumer clientId=consumer-1, groupId=my-fifth-application] Setting newly assigned partitions [first_topic-1]

---- after running: ProducerDemoKeys
[main] INFO com.github.stefk.kafka.tutorial1.ConsumerDemoGroups - key: id_0, Value: Hello World 0
[main] INFO com.github.stefk.kafka.tutorial1.ConsumerDemoGroups - Partition: 1, Offset: 25
[main] INFO com.github.stefk.kafka.tutorial1.ConsumerDemoGroups - key: id_8, Value: Hello World 8
[main] INFO com.github.stefk.kafka.tutorial1.ConsumerDemoGroups - Partition: 1, Offset: 26


--- Consumer B [first_topic-0]
[main] INFO org.apache.kafka.clients.consumer.internals.AbstractCoordinator - [Consumer clientId=consumer-1, groupId=my-fifth-application] Attempt to heartbeat failed since group is rebalancing
[main] INFO org.apache.kafka.clients.consumer.internals.ConsumerCoordinator - [Consumer clientId=consumer-1, groupId=my-fifth-application] Revoking previously assigned partitions [first_topic-0, first_topic-1]
[main] INFO org.apache.kafka.clients.consumer.internals.AbstractCoordinator - [Consumer clientId=consumer-1, groupId=my-fifth-application] (Re-)joining group
[main] INFO org.apache.kafka.clients.consumer.internals.AbstractCoordinator - [Consumer clientId=consumer-1, groupId=my-fifth-application] Successfully joined group with generation 10
[main] INFO org.apache.kafka.clients.consumer.internals.ConsumerCoordinator - [Consumer clientId=consumer-1, groupId=my-fifth-application] Setting newly assigned partitions [first_topic-0]

---- after running: ProducerDemoKeys
[main] INFO com.github.stefk.kafka.tutorial1.ConsumerDemoGroups - key: id_1, Value: Hello World 1
[main] INFO com.github.stefk.kafka.tutorial1.ConsumerDemoGroups - Partition: 0, Offset: 32
[main] INFO com.github.stefk.kafka.tutorial1.ConsumerDemoGroups - key: id_3, Value: Hello World 3
[main] INFO com.github.stefk.kafka.tutorial1.ConsumerDemoGroups - Partition: 0, Offset: 33
[main] INFO com.github.stefk.kafka.tutorial1.ConsumerDemoGroups - key: id_6, Value: Hello World 6
[main] INFO com.github.stefk.kafka.tutorial1.ConsumerDemoGroups - Partition: 0, Offset: 34


--- Consumer C [first_topic-2]
[main] INFO org.apache.kafka.clients.consumer.internals.AbstractCoordinator - [Consumer clientId=consumer-1, groupId=my-fifth-application] Discovered group coordinator PC-HEM00018977.rce-group.local:9092 (id: 2147483647 rack: null)
[main] INFO org.apache.kafka.clients.consumer.internals.ConsumerCoordinator - [Consumer clientId=consumer-1, groupId=my-fifth-application] Revoking previously assigned partitions []
[main] INFO org.apache.kafka.clients.consumer.internals.AbstractCoordinator - [Consumer clientId=consumer-1, groupId=my-fifth-application] (Re-)joining group
[main] INFO org.apache.kafka.clients.consumer.internals.AbstractCoordinator - [Consumer clientId=consumer-1, groupId=my-fifth-application] Successfully joined group with generation 10
[main] INFO org.apache.kafka.clients.consumer.internals.ConsumerCoordinator - [Consumer clientId=consumer-1, groupId=my-fifth-application] Setting newly assigned partitions [first_topic-2]

---- after running: ProducerDemoKeys
[main] INFO com.github.stefk.kafka.tutorial1.ConsumerDemoGroups - key: id_2, Value: Hello World 2
[main] INFO com.github.stefk.kafka.tutorial1.ConsumerDemoGroups - Partition: 2, Offset: 37
[main] INFO com.github.stefk.kafka.tutorial1.ConsumerDemoGroups - key: id_4, Value: Hello World 4
[main] INFO com.github.stefk.kafka.tutorial1.ConsumerDemoGroups - Partition: 2, Offset: 38
[main] INFO com.github.stefk.kafka.tutorial1.ConsumerDemoGroups - key: id_5, Value: Hello World 5
[main] INFO com.github.stefk.kafka.tutorial1.ConsumerDemoGroups - Partition: 2, Offset: 39
[main] INFO com.github.stefk.kafka.tutorial1.ConsumerDemoGroups - key: id_7, Value: Hello World 7
[main] INFO com.github.stefk.kafka.tutorial1.ConsumerDemoGroups - Partition: 2, Offset: 40
[main] INFO com.github.stefk.kafka.tutorial1.ConsumerDemoGroups - key: id_9, Value: Hello World 9
[main] INFO com.github.stefk.kafka.tutorial1.ConsumerDemoGroups - Partition: 2, Offset: 41




> 50. Java Consumer with Threads
ConsumerDemoWithThread.class

-- on exit (press):

[Thread-2] INFO com.github.stefk.kafka.tutorial1.ConsumerDemoWithThread$ConsumerRunnable - Caught shutdown hook
[Thread-1] INFO com.github.stefk.kafka.tutorial1.ConsumerDemoWithThread$ConsumerRunnable - Received shutdown signal!
[main] INFO com.github.stefk.kafka.tutorial1.ConsumerDemoWithThread$ConsumerRunnable - Application is closing
[Thread-2] INFO com.github.stefk.kafka.tutorial1.ConsumerDemoWithThread$ConsumerRunnable - Application has exited


> 51. Java Consumer Seek and Assign
ConsumerDemoAssignAndSeek.class
// no specify 'groupId' on purpose
// assign and seek are mostly used to replay data or fetch a specific message


> 52. Client Bi-Directional Compatibility

Bottom Line: Always use the latest version of client

> 53. Configuring Producers and Consumers

```
Client Configurations
There exist a lot of options to:

configure producer: https://kafka.apache.org/documentation/#producerconfigs

configure consumers:  https://kafka.apache.org/documentation/#consumerconfigs

The most important options are discussed in the real-world project section, coming next

Happy learning!
```



-----------------------------------------
-----------------------------------------

Section 8: ===== Kafka Real World Project =====

> 55. Real World Exercise

```
Real World Exercise
Real-World Exercise:
Before jumping to the next section for the solution, here are some pointers for some exercises:

Twitter Producer

The Twitter Producer gets data from Twitter based on some keywords and put them in a Kafka topic of your choice

Twitter Java Client: https://github.com/twitter/hbc

Twitter API Credentials: https://developer.twitter.com/

ElasticSearch Consumer

The ElasticSearch Consumer gets data from your twitter topic and inserts it into ElasticSearch

ElasticSearch Java Client: https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.4/java-rest-high.html

ElasticSearch setup:

https://www.elastic.co/guide/en/elasticsearch/reference/current/setup.html

OR https://bonsai.io/

Good luck!
```



