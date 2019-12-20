package com.github.stefk.kafka.tutorial1;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.protocol.types.Field.Str;
import org.apache.kafka.common.serialization.StringSerializer;

public class ProducerDemo {

  public static void main(String[] args) {
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

    // create a producer record
    ProducerRecord<String, String> record = new ProducerRecord<String, String>("first_topic", "hello world 3"
        + "");

    // send data - asynchronous
    producer.send(record);

    // flush data
//    producer.flush();

    // flush and close producer
    producer.close();

  }

}
