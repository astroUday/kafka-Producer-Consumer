package com.demo.kafka.controller;

import com.demo.kafka.listner.KafkaListeners;
import com.demo.kafka.model.Book;
import com.demo.kafka.model.ConsumerDto;
import com.demo.kafka.model.LibraryEvent;
import com.demo.kafka.model.LibraryEventType;
import com.demo.kafka.producer.KafkaProducer;
import com.demo.kafka.service.ListenerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/msg")
public class MessageController {
    private final KafkaProducer producer;
    private final ListenerService listenerService;

    //event for new book update
    @PostMapping
    public ResponseEntity<?> publish(@RequestBody LibraryEvent libraryEvent) throws JsonProcessingException {
        libraryEvent.setLibraryEventType(LibraryEventType.NEW);
        producer.produceEvent(libraryEvent);
        KafkaListeners kafkaListeners=new KafkaListeners();
        return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
    }

    //event for updated book
    @PutMapping
    public ResponseEntity<?> update(@RequestBody LibraryEvent libraryEvent) throws JsonProcessingException {

        if(libraryEvent.getLibraryEventType()==null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please pass the LibraryEventId");

        libraryEvent.setLibraryEventType(LibraryEventType.UPADTE);
        producer.produceEvent(libraryEvent);
        return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
    }
    @GetMapping
    public ResponseEntity<?> getLibraryEvent(){
        Book book=new Book(1,"The black Swan","Nassim Nicholas");
        LibraryEvent libraryEvent=new LibraryEvent();
        libraryEvent.setId(1);
        libraryEvent.setBook(book);
        libraryEvent.setLibraryEventType(LibraryEventType.NEW);
        return ResponseEntity.ok(libraryEvent);
    }
    @PostMapping("/test-api")
    public ResponseEntity sendEvents(@RequestBody ConsumerDto consumerDto, @RequestParam("topic") String topic){
        producer.sendEvent(consumerDto, topic);
        return ResponseEntity.ok(consumerDto);
    }
}
