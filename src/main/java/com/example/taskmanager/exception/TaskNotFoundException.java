package com.example.taskmanager.exception;

public class TaskNotFoundException extends RuntimeException {

    private final String message;

    private final Object data;

    public TaskNotFoundException(String message, Object data) {
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
