package com.github.stefk.kafka.tutorial1;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerDemoKeys {

  public static void main(String[] args) throws ExecutionException, InterruptedException {

    final Logger logger = LoggerFactory.getLogger(ProducerDemoKeys.class);

    String bootstrapServes = "127.0.0.1:9092";

    // create Producer properties
    // refer to kafka-documentation: https://kafka.apache.org/documentation/#producerconfigs
    Properties properties = new Properties();
//    properties.setProperty("bootstrap.servers", bootstrapServes);
    properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServes);
//    properties.setProperty("key.serializer", StringSerializer.class.getName());
    properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//    properties.setProperty("value.serializer", StringSerializer.class.getName());
    properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

      // create the producer
      KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

    for(int i=0; i<10; i++){
      // create a producer record
      String topic = "first_topic";
      String value = "Hello World " + Integer.toString(i);
      String key = "id_" + Integer.toString(i);

      ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, key, value);

      logger.info("Key: " + key);  // log the key
      // id_0 is going to partition 1
      // id_1 is going to partition 0
      // id_2 is going to partition 2
      // id_3 is going to partition 0
      // id_4 is going to partition 2
      // id_5 is going to partition 2
      // id_6 is going to partition 0
      // id_7 is going to partition 2
      // id_8 is going to partition 1
      // id_9 is going to partition 2

      // send data - asynchronous
      producer.send(record, new Callback() {
        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
          // executes every time a record is successfully sent or an exception is thrown
          if (e == null) {
            // the record was successfully sent
            logger.info("Received new metadata. \n" +
                "Topic: " + recordMetadata.topic() + "\n" +
                "Partition: " + recordMetadata.partition() + "\n" +
                "Offset: " + recordMetadata.offset() + "\n" +
                "Timestamp: " + recordMetadata.timestamp());
          } else {
            logger.error("Error while producing", e);

          }
        }
      }).get(); // block the .send() to make it synchronous - don't do this in production!
    }

    // flush data
//    producer.flush();

    // flush and close producer
    producer.close();

  }

}
