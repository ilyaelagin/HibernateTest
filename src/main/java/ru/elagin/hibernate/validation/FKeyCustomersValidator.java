package ru.elagin.hibernate.validation;

import org.springframework.beans.factory.annotation.Autowired;
import ru.elagin.hibernate.models.Customer;
import ru.elagin.hibernate.response.Response;
import ru.elagin.hibernate.services.CustomerService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FKeyCustomersValidator implements ConstraintValidator<FKeyCustomers, Customer> {

    @Autowired
    private CustomerService customerService;

    @Override
    public void initialize(FKeyCustomers constraintAnnotation) {
    }

    @Override
    public boolean isValid(Customer customer, ConstraintValidatorContext validatorContext) {

        if (customerService == null)
            return true;
        if (customer.getId() == null)
            return true;

        Response response = customerService.show(customer.getId());

        if (response.getError() != null) {
            return true;
        }

        if (response.getObject() == null) {
            validatorContext.disableDefaultConstraintViolation();
            validatorContext.buildConstraintViolationWithTemplate("{validation.customer.foreign.key.error}")
                    .addConstraintViolation();

            return false;
        }
        return true;
    }
}
