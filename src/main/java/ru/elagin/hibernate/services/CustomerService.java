package ru.elagin.hibernate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.elagin.hibernate.dto.CustomerFilter;
import ru.elagin.hibernate.models.Customer;
import ru.elagin.hibernate.repository.CustomerRepository;
import ru.elagin.hibernate.response.CustomerResponse;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerResponse index(CustomerFilter customerFilter) {
        CustomerResponse response = new CustomerResponse();
        try {
            response.setCustomerList(customerRepository.index(customerFilter));
        } catch (Exception e) {
            response.setError("Error occurred during the show all customers");
        }
        return response;
    }

    public CustomerResponse show(Integer id) {
        CustomerResponse response = new CustomerResponse();
        try {
            response.setCustomer(customerRepository.show(id));
        } catch (Exception e) {
            response.setError("Error occurred during show data");
        }
        return response;
    }

    public CustomerResponse save(Customer customer) {
        try {
            customerRepository.save(customer);
            return new CustomerResponse();
        } catch (Exception e) {
            CustomerResponse response = new CustomerResponse();
            response.setError("Error occurred during save data");
            return response;
        }
    }

    public CustomerResponse update(Integer id, Customer customer) {
        try {
            customerRepository.update(id, customer);
            return new CustomerResponse();
        } catch (Exception e) {
            CustomerResponse response = new CustomerResponse();
            response.setError("Error occurred during update data");
            return response;
        }
    }

    public CustomerResponse delete(Integer id) {
        try {
            customerRepository.delete(id);
            return new CustomerResponse();
        } catch (Exception e) {
            CustomerResponse response = new CustomerResponse();
            response.setError("Error occurred during delete data");
            return response;
        }
    }
}
