package com.example.repository;

import com.example.model.UserEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class InMemoryUserRepository implements UserRepository {

    @Override
    public UserEntity find(String login) {
        // Simulates finding a user by login
        return new UserEntity(UUID.randomUUID(), login);
    }
}
