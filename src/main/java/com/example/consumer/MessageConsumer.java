package com.example.consumer;

import com.example.event.Message;
import com.example.model.UserEntity;
import com.example.processor.MessageProcessor;
import com.example.repository.UserRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    private final UserRepository userRepository;
    private final MessageProcessor messageProcessor;

    public MessageConsumer(UserRepository userRepository, MessageProcessor messageProcessor) {
        this.userRepository = userRepository;
        this.messageProcessor = messageProcessor;
    }

    @KafkaListener(
        topics = "${kafka.topics.messages}",
        groupId = "${kafka.groups.processing}",
        id = "message-processing",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(Message message) {
        UserEntity user = userRepository.find(message.author());
        messageProcessor.process(user, message);
    }
}
