package com.example.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class SystemClock implements Clock {
    @Override
    public LocalDate now() {
        return LocalDate.now();
    }
}
