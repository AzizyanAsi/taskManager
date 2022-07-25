package com.example.taskmanager.exception;


import com.example.taskmanager.validation.ValidationError;
import lombok.Getter;

public class ViolationException extends RuntimeException {

    @Getter
    private final ValidationError validationError;

    public ViolationException(ValidationError validationError) {
        this.validationError = validationError;
    }

}
