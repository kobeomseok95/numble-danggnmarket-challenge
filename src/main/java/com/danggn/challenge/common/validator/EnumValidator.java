package com.danggn.challenge.common.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class EnumValidator implements ConstraintValidator<EnumValid, String> {

    private EnumValid annotation;

    @Override
    public void initialize(EnumValid constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Enum<?>[] enumValues = this.annotation.enumClass().getEnumConstants();
        if (enumValues == null) return false;
        return Arrays.stream(enumValues)
                .anyMatch(anEnum ->
                        anEnum.name().equals(value.toUpperCase()));
    }
}
