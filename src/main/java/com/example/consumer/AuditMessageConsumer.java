package com.example.consumer;

import com.example.event.Message;
import com.example.model.AuditLog;
import com.example.service.AuditService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AuditMessageConsumer {

    private final AuditService auditService;

    public AuditMessageConsumer(AuditService auditService) {
        this.auditService = auditService;
    }

    @KafkaListener(
        topics = "${kafka.topics.messages}",
        groupId = "${kafka.groups.audit}",
        id = "audit",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(Message message) {
        AuditLog log = new AuditLog(message.author(), message.title());
        auditService.info(log);
    }
}
