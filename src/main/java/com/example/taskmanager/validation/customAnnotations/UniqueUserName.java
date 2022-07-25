package com.example.taskmanager.validation.customAnnotations;

import com.example.taskmanager.validation.UniqueUserNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = UniqueUserNameValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface UniqueUserName {

    String message() default "There is already have user with this username!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}