package com.example.model;

import java.time.LocalDate;

public record MessageEntity(LocalDate receiveDate, String author, String title, String payload) {}
