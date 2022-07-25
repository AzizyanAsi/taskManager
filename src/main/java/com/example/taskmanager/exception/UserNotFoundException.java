package com.example.taskmanager.exception;

public class UserNotFoundException  extends RuntimeException {

    private final String message;

    private final Object data;

    public UserNotFoundException(String message, Object data) {
        super(message);
        this.message = message;
        this.data = data;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}
