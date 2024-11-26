package org.microservice.screen.validators.vipFromRow;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = VipFromRowValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface VipFromRow {
    String message() default "VIP Seat must not be below 'C' row";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
