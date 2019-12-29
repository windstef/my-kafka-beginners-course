package com.github.stefk.kafka.tutorial1.utils;

import java.util.Map;

import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.stefk.kafka.tutorial1.ProducerDemoWithCallBack;

public class KafkaJsonSerializer implements Serializer {

//  private Logger logger = LogManager.getLogger(this.getClass());
  final org.slf4j.Logger logger = LoggerFactory.getLogger(ProducerDemoWithCallBack.class);


  @Override
  public void configure(Map map, boolean b) {

  }

  @Override
  public byte[] serialize(String s, Object o) {
    byte[] retVal = null;
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      retVal = objectMapper.writeValueAsBytes(o);
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return retVal;
  }

  @Override
  public void close() {

  }
}
