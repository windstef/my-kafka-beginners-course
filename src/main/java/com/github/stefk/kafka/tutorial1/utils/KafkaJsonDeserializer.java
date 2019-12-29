package com.github.stefk.kafka.tutorial1.utils;

import java.util.Map;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.stefk.kafka.tutorial1.ProducerDemoWithCallBack;

public class KafkaJsonDeserializer<T> implements Deserializer {

//  private Logger logger = LogManager.getLogger(this.getClass());
  final org.slf4j.Logger logger = LoggerFactory.getLogger(ProducerDemoWithCallBack.class);


  private Class <T> type;

  public KafkaJsonDeserializer(Class type) {
    this.type = type;
  }

  @Override
  public void configure(Map map, boolean b) {

  }

  @Override
  public Object deserialize(String s, byte[] bytes) {
    ObjectMapper mapper = new ObjectMapper();
    T obj = null;
    try {
      obj = mapper.readValue(bytes, type);
    } catch (Exception e) {

      logger.error(e.getMessage());
    }
    return obj;
  }

  @Override
  public void close() {

  }
}
