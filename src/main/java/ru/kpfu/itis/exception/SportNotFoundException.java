package ru.kpfu.itis.exception;

public class SportNotFoundException extends RuntimeException {
    public SportNotFoundException(String message) {
        super(message);
    }
}
