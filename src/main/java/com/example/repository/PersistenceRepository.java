package com.example.repository;

import com.example.model.MessageEntity;

public interface PersistenceRepository {
    void save(MessageEntity messageEntity);
}
