package ru.kpfu.itis.exception;

public class ScheduleTrainingNotFoundException extends RuntimeException {
    public ScheduleTrainingNotFoundException(String message) {
        super(message);
    }
}
