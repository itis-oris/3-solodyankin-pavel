package ru.kpfu.itis.exception;

public class CoachNotFoundException extends RuntimeException {
    public CoachNotFoundException(String message) {
        super(message);
    }
}
