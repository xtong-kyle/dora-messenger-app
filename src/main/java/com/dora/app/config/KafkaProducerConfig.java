package com.dora.app.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Bean
    public DefaultKafkaProducerFactory<String, String> stringProducerFactory() {
        Map<String, Object> producerFactoryConfig = new HashMap<>();
        producerFactoryConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        producerFactoryConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        producerFactoryConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        producerFactoryConfig.put(ProducerConfig.ACKS_CONFIG, "all");
        producerFactoryConfig.put(ProducerConfig.RETRIES_CONFIG, 3);
        producerFactoryConfig.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 3355432);
        return new DefaultKafkaProducerFactory<>(producerFactoryConfig);
    }

    @Bean
    public KafkaTemplate<String, String> stringToStringKafkaTemplate() {
        return new KafkaTemplate<>(stringProducerFactory());
    }

}
