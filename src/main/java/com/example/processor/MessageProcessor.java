package com.example.processor;

import com.example.event.Message;
import com.example.model.UserEntity;

public interface MessageProcessor {
    void process(UserEntity user, Message message);
}
