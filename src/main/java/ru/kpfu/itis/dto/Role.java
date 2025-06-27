package ru.kpfu.itis.dto;

import lombok.Getter;

@Getter
public enum Role {
    COACH("Тренер"),
    SPORTSMAN("Спортсмен");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
