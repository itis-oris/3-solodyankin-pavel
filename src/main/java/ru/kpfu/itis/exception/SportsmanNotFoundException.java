package ru.kpfu.itis.exception;

public class SportsmanNotFoundException extends RuntimeException {
    public SportsmanNotFoundException(String message) {
        super(message);
    }
}