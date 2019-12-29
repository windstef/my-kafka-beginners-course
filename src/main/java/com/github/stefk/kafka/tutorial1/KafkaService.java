package com.github.stefk.kafka.tutorial1;

import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import com.github.stefk.kafka.tutorial1.utils.CustomObject;
import com.github.stefk.kafka.tutorial1.utils.KafkaJsonDeserializer;
import com.github.stefk.kafka.tutorial1.utils.KafkaJsonSerializer;

public class KafkaService {
  private final Producer<String, CustomObject> kafkaProducer;
  private final Consumer<String, CustomObject> consumer ;
  private final String topic = "queuing.mission";


  public KafkaService() {

    Properties props = new Properties();
    props.put("bootstrap.servers", "127.0.0.1:9092");
//    props.put("group.id", "test");
    props.put("group.id", "my-fourth-application");
    props.put("enable.auto.commit", "true");
    props.put("auto.commit.interval.ms", "1000");
    props.put("session.timeout.ms", "30000");



    kafkaProducer =new KafkaProducer<String,CustomObject>(
        props,
        new StringSerializer(),
        new KafkaJsonSerializer()
    );

    consumer =
        new KafkaConsumer<String, CustomObject>(props, new StringDeserializer() ,new KafkaJsonDeserializer<CustomObject>(CustomObject.class));
  }


  public void send(CustomObject res) {
    kafkaProducer.send(new ProducerRecord<>(topic, res.getId(),  res));

  }

  public Consumer consume(String topic){
    consumer.subscribe(Collections.singletonList(topic));
    return consumer;
  }

}
