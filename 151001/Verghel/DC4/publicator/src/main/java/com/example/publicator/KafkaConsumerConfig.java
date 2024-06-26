package com.example.publicator;

import com.example.publicator.dto.MessageRequestTo;
import com.example.publicator.dto.MessageResponseTo;
import com.example.publicator.dto.MessageResponseToSerializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {
    @Bean
    public KafkaConsumer<String, MessageResponseTo> kafkaMessageConsumer() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, MessageResponseToSerializer.class.getName());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "OutTopic");
        KafkaConsumer<String, MessageResponseTo> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("OutTopic"));
        return consumer;
    }

}