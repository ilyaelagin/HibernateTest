package ru.elagin.hibernate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.elagin.hibernate.dto.CustomerFilter;
import ru.elagin.hibernate.models.Customer;
import ru.elagin.hibernate.repository.CustomerRepository;
import ru.elagin.hibernate.response.Response;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Response index(CustomerFilter customerFilter) {
        Response response = new Response();
        try {
            response.setObjects(customerRepository.index(customerFilter));
        } catch (Exception e) {
            response.setError("Error occurred during the show all customers");
        }
        return response;
    }

    public Response index() {
        Response response = new Response();
        try {
            response.setObjects(customerRepository.index());
        } catch (Exception e) {
            response.setError("Error occurred during the show all customers");
        }
        return response;
    }

    public Response show(Integer id) {
        Response response = new Response();
        try {
            if (customerRepository.show(id) != null)
                response.setObject(customerRepository.show(id));
        } catch (Exception e) {
            response.setError("Error occurred during show data");
        }
        return response;
    }

    public Response save(Customer customer) {
        try {
            customerRepository.save(customer);
            return new Response();
        } catch (Exception e) {
            Response response = new Response();
            response.setError("Error occurred during save data");
            return response;
        }
    }

    public Response update(Integer id, Customer customer) {
        try {
            customerRepository.update(id, customer);
            return new Response();
        } catch (Exception e) {
            Response response = new Response();
            response.setError("Error occurred during update data");
            return response;
        }
    }

    public Response delete(Integer id) {
        try {
            customerRepository.delete(id);
            return new Response();
        } catch (Exception e) {
            Response response = new Response();
            response.setError("Error occurred during delete data");
            return response;
        }
    }
}
