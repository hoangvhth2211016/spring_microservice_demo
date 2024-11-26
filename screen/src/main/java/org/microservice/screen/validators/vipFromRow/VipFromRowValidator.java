package org.microservice.screen.validators.vipFromRow;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class VipFromRowValidator implements ConstraintValidator<VipFromRow, Character> {
    @Override
    public boolean isValid(Character value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value >= 'C';
    }
}