package com.example.consumer;

import com.example.event.Message;
import com.example.model.MessageEntity;
import com.example.repository.PersistenceRepository;
import com.example.util.Clock;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PersistenceMessageConsumer {

    private final PersistenceRepository persistenceRepository;
    private final Clock clock;

    public PersistenceMessageConsumer(PersistenceRepository persistenceRepository, Clock clock) {
        this.persistenceRepository = persistenceRepository;
        this.clock = clock;
    }

    @KafkaListener(
        topics = "${kafka.topics.messages}",
        groupId = "${kafka.groups.persistence}",
        id = "persistence",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(Message message) {
        MessageEntity entity = new MessageEntity(
            clock.now(),
            message.author(),
            message.title(),
            message.payload()
        );
        persistenceRepository.save(entity);
    }
}
