package com.github.stefk.kafka.tutorial1;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class ProducerForMissionRouteGSL {

  public static void main(String[] args) throws ExecutionException, InterruptedException {

    final Logger logger = LoggerFactory.getLogger(ProducerForMissionRouteGSL.class);

    String bootstrapServes = "127.0.0.1:9092";

    // create Producer properties
    // refer to kafka-documentation: https://kafka.apache.org/documentation/#producerconfigs
    Properties properties = new Properties();
    properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServes);
    properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

      // create the producer
      KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

    for(int i=0; i<10; i++){
      // create a producer record
      String topic = "queuing.mission";
      String value = "Hello World " + Integer.toString(i);
      String key = "id_" + Integer.toString(i);

      ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, key, value);

      logger.info("Key: " + key);  // log the key

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
