package com.example.repository;

import com.example.model.MessageEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class InMemoryPersistenceRepository implements PersistenceRepository {

    private final List<MessageEntity> store = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void save(MessageEntity messageEntity) {
        store.add(messageEntity);
        System.out.println("[PERSISTENCE] Saved: " + messageEntity);
    }

    public List<MessageEntity> findAll() {
        return Collections.unmodifiableList(store);
    }
}
