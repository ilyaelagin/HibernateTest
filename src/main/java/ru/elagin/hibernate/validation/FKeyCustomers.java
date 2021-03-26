package ru.elagin.hibernate.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FKeyCustomersValidator.class)

public @interface FKeyCustomers {

    String message() default "validation.customer.error";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
