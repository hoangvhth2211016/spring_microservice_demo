package org.microservice.screen.validators.numbersOfCoupleSeat;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NumbersOfCoupleSeatValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface NumbersOfCoupleSeat {
    String message() default "Number of couple seats must not be greater than the number of columns";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}