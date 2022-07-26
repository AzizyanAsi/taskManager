package com.example.taskmanager.validation.customAnnotations;


import com.example.taskmanager.validation.UniqueEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = UniqueEmailValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface UniqueEmail {

    String message() default "There is already have user with this email!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

