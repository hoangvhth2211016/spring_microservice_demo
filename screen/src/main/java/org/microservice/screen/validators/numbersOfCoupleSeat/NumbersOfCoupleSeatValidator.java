package org.microservice.screen.validators.numbersOfCoupleSeat;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.microservice.screen.models.screens.ScreenCreateReq;

public class NumbersOfCoupleSeatValidator implements ConstraintValidator<NumbersOfCoupleSeat, ScreenCreateReq> {
    @Override
    public boolean isValid(ScreenCreateReq req, ConstraintValidatorContext context) {
        if (req.getNumberOfCouple() == null || req.getNumbersOfColumn() == null) {
            return true; // Null values can be handled with @NotNull annotation
        }
        return req.getNumberOfCouple() <= req.getNumbersOfColumn();
    }
}
