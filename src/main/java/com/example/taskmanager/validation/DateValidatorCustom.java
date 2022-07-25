package com.example.taskmanager.validation;

import com.example.taskmanager.validation.customAnnotations.ValidDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateValidatorCustom implements
        ConstraintValidator<ValidDate, String> {

    public final static String DATE_FORMAT_PATTERN = "yyyy-MM-dd";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_PATTERN);
            sdf.parse(value);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

}

