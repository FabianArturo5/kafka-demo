package com.example.producer;

import com.example.event.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

    private final KafkaTemplate<String, Message> kafkaTemplate;

    @Value("${kafka.topics.messages}")
    private String topic;

    public MessageProducer(KafkaTemplate<String, Message> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(Message message) {
        kafkaTemplate.send(topic, message);
        System.out.println("[PRODUCER] Sent: " + message);
    }
}
