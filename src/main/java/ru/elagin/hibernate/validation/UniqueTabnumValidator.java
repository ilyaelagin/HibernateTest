package ru.elagin.hibernate.validation;

import org.springframework.beans.factory.annotation.Autowired;
import ru.elagin.hibernate.models.Customer;
import ru.elagin.hibernate.repository.CustomerRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class UniqueTabnumValidator implements ConstraintValidator<UniqueTabnum, Customer> {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void initialize(UniqueTabnum constraintAnnotation) {
    }

    @Override
    public boolean isValid(Customer customer, ConstraintValidatorContext validatorContext) {

        if (customerRepository == null)
            return true;
        if (customer.getTabnum() == null)
            return true;

        List<Customer> customerList = customerRepository.index();

        for (Customer client : customerList) {

            if (customer.getId() == null && customer.getTabnum().equals(client.getTabnum()) ||
                    customer.getTabnum().equals(client.getTabnum()) && !customer.getId().equals(client.getId())) {

                validatorContext.disableDefaultConstraintViolation();
                validatorContext.buildConstraintViolationWithTemplate("{validation.unique.tabnum.error}")
                        .addPropertyNode("tabnum").addConstraintViolation();

                return false;
            }
        }
        return true;
    }
}
