package com.example.taskmanager.validation.customAnnotations;


import com.example.taskmanager.validation.DateValidatorCustom;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateValidatorCustom.class)
public @interface ValidDate {

    String message() default "The date must have correct style like YYYY/DD/MM";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
