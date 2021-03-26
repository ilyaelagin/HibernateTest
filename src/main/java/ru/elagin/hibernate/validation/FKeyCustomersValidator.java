package ru.elagin.hibernate.validation;

import org.springframework.beans.factory.annotation.Autowired;
import ru.elagin.hibernate.models.Customer;
import ru.elagin.hibernate.repository.CustomerRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FKeyCustomersValidator implements ConstraintValidator<FKeyCustomers, Customer> {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void initialize(FKeyCustomers constraintAnnotation) {
    }

    @Override
    public boolean isValid(Customer customer, ConstraintValidatorContext validatorContext) {

        if (customerRepository == null)
            return true;
        if (customer.getId() == null)
            return true;

        if (customerRepository.show(customer.getId()) == null) {
            validatorContext.disableDefaultConstraintViolation();
            validatorContext.buildConstraintViolationWithTemplate("{validation.customer.foreign.key.error}")
                    .addConstraintViolation();

            return false;
        }
        return true;
    }
}
