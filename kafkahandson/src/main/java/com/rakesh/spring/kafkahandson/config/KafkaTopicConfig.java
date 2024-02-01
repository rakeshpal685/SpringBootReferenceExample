package com.rakesh.spring.kafkahandson.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    //Here we are creating the topic where my producer will send the messages
  @Bean
  public NewTopic rakeshTopic() {
    return TopicBuilder.name("rakesh").build();
  }
}
