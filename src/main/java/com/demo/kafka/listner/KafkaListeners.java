package com.demo.kafka.listner;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {
    @KafkaListener(topics="library-events", groupId = "rating-service")
    public void listener1(String data){
        System.out.println("RECEIVED (1) : "+data+ " ;) ");
    }
    @KafkaListener(topics="library-events", groupId = "rating-service")
    public void listener2(String data){
        System.out.println("RECEIVED (2) : "+data+ " ;) ");
    }
}
