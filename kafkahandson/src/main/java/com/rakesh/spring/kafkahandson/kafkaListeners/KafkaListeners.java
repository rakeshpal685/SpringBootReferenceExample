package com.rakesh.spring.kafkahandson.kafkaListeners;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

//Here groupId is used to listen from the topic/partition when we scale our application
    @KafkaListener(topics="mukesh",groupId = "giveGroupIdHere")
    void listener(String data){
    System.out.println("Listener Received : " + data+ "🎉🎉");
    }
}
