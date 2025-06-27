package ru.kpfu.itis.exception;

import lombok.Getter;
import ru.kpfu.itis.dto.request.UserRequest;

@Getter
public class EmailAlreadyExistsException extends RuntimeException {

    private final UserRequest userRequest;

    public EmailAlreadyExistsException(String message,UserRequest userRequest) {
        super(message);
        this.userRequest = userRequest;
    }
    public EmailAlreadyExistsException(String message) {
        super(message);
        this.userRequest = null;
    }
}
