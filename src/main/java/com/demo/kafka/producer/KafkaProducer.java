package com.demo.kafka.producer;

import com.demo.kafka.model.ConsumerDto;
import com.demo.kafka.model.LibraryEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.TimeoutException;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {
    @Autowired
    KafkaTemplate<Integer,String> template;
    @Autowired
    KafkaTemplate<Integer,ConsumerDto> kafkaTemplate;
    String topic = "library-events";
    @Autowired
    ObjectMapper objectMapper;

    public CompletableFuture<SendResult<Integer, String>> produceEvent(LibraryEvent libraryEvent) throws JsonProcessingException {
        Integer key = libraryEvent.getId();
        String value = objectMapper.writeValueAsString(libraryEvent);
        ProducerRecord<Integer,String> producerRecord = buildProducerRecord(key, value, topic);
        ProducerRecord<Integer,String> producerRecord1=new ProducerRecord<>(topic,key,key,value);
//        template.send(topic,value);
        template.send(producerRecord1);
        return new CompletableFuture<>();
    }

    private ProducerRecord<Integer, String> buildProducerRecord(int key, String value, String topic) {
        List<Header> recordHeaders = List.of(new RecordHeader("authentication", "Bearer 1234567890".getBytes()));
        return new ProducerRecord<>(topic, key, key, value, recordHeaders);
    }

    public SendResult<Integer, String> sendLibraryEventSynchronous(LibraryEvent libraryEvent) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException, java.util.concurrent.TimeoutException {

        Integer key = Integer.valueOf(libraryEvent.getId());
        String value = objectMapper.writeValueAsString(libraryEvent);
        SendResult<Integer,String> sendResult=null;
        try {
            sendResult = template.sendDefault(key,value).get(1, TimeUnit.SECONDS);
        } catch (ExecutionException | InterruptedException e) {
            log.error("ExecutionException/InterruptedException Sending the Message and the exception is {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Exception Sending the Message and the exception is {}", e.getMessage());
            throw e;
        }
        return sendResult;
    }

    public void sendEvent(ConsumerDto consumerDto, String event) {
        List<Header> recordHeaders = List.of(new RecordHeader("authentication", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqYXZhQHlvcG1haWwuY29tIiwiaWF0IjoxNjkyNjIxMzQ3LCJleHAiOjYwMTY5MjYyMTM0N30.udRth2XuWPdVEegiZ2jGDz7V9G6M_80K7xz_2IrtFFg".getBytes()));
        ProducerRecord<Integer,ConsumerDto> producerRecord=new ProducerRecord<>(event,null,null,null,consumerDto,recordHeaders);
        System.out.println("Sent !! ");
        kafkaTemplate.send(producerRecord);
    }
}
