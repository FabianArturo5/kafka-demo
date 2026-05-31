package com.example.repository;

import com.example.model.UserEntity;

public interface UserRepository {
    UserEntity find(String login);
}
