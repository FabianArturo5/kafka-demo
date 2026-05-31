package com.example.processor;

import com.example.event.Message;
import com.example.model.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class DefaultMessageProcessor implements MessageProcessor {

    @Override
    public void process(UserEntity user, Message message) {
        System.out.println("[PROCESSOR] Processing message for user='" + user.login()
                + "' | title='" + message.title()
                + "' | payload='" + message.payload() + "'");
    }
}
