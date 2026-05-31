package com.example.service;

import com.example.model.AuditLog;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class LogAuditService implements AuditService {

    private final List<AuditLog> logs = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void info(AuditLog log) {
        logs.add(log);
        System.out.println("[AUDIT] " + log);
    }

    public List<AuditLog> findAll() {
        return Collections.unmodifiableList(logs);
    }
}
