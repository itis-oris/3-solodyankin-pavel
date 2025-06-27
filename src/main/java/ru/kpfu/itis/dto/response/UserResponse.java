package ru.kpfu.itis.dto.response;

public record UserResponse(
        String name,
        Integer age,
        String phone,
        String rank,
        String sport
){}

