package com.demo.kafka.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListenerService {
//    @KafkaListener(topics="library-events", groupId = "rating-service")
//    void listener1(Object data){
//        System.out.println("RECEIVED (1) : "+data+ " ;) ");
//    }
//    @KafkaListener(topics="library-events", groupId = "rating-service")
//    void listener2(Object data){
//        System.out.println("RECEIVED (2) : "+data+ " ;) ");
//    }
}
