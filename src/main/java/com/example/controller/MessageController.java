package com.example.controller;

import com.example.event.Message;
import com.example.model.AuditLog;
import com.example.model.MessageEntity;
import com.example.producer.MessageProducer;
import com.example.repository.InMemoryPersistenceRepository;
import com.example.service.LogAuditService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageProducer messageProducer;
    private final InMemoryPersistenceRepository persistenceRepository;
    private final LogAuditService auditService;

    public MessageController(MessageProducer messageProducer,
                             InMemoryPersistenceRepository persistenceRepository,
                             LogAuditService auditService) {
        this.messageProducer = messageProducer;
        this.persistenceRepository = persistenceRepository;
        this.auditService = auditService;
    }

    // Send a message to Kafka
    @PostMapping
    public ResponseEntity<String> send(@RequestBody Message message) {
        messageProducer.send(message);
        return ResponseEntity.ok("Message sent: " + message);
    }

    // Query all persisted messages
    @GetMapping("/persisted")
    public ResponseEntity<List<MessageEntity>> getPersisted() {
        return ResponseEntity.ok(persistenceRepository.findAll());
    }

    // Query all audit logs
    @GetMapping("/audit")
    public ResponseEntity<List<AuditLog>> getAuditLogs() {
        return ResponseEntity.ok(auditService.findAll());
    }
}
