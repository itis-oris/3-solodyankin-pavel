package ru.kpfu.itis.dto.request;

public record SmsRequest(
        String to,
        String message
) {}
