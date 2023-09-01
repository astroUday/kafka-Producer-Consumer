package com.demo.kafka.config;

import com.demo.kafka.model.ConsumerDto;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootStrapService;

    public Map<String,Object> producerConfig(){
        Map<String,Object> prop = new HashMap<>();

        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootStrapService);
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return prop;
    }
    @Bean
    public ProducerFactory<Integer, String> producerFactory(){
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }
    @Bean
    public ProducerFactory<Integer, ConsumerDto> producerFactory2(){
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }
    @Bean
    public KafkaTemplate<Integer,String> kafkaTemplate(
            ProducerFactory<Integer, String> producerFactory ){
        return new KafkaTemplate<>(producerFactory());
    }
    @Bean
    public KafkaTemplate<Integer, ConsumerDto> kafkaTemplates(
            ProducerFactory<Integer, String> producerFactory ){
        return new KafkaTemplate<>(producerFactory2());
    }

}
