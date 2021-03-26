package ru.elagin.hibernate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.elagin.hibernate.dto.CustomerFilter;
import ru.elagin.hibernate.models.Customer;
import ru.elagin.hibernate.repository.AutoRepository;
import ru.elagin.hibernate.repository.CustomerRepository;

import javax.validation.Valid;

@Controller
@RequestMapping("/customers")
public class CustomersController {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomersController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping()
    public String index(Model model, @ModelAttribute("customerFilter") @Valid CustomerFilter customerFilter, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return "customers/index";
        model.addAttribute("customers", customerRepository.index(customerFilter));
        return "customers/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model modelCustomer) {
        modelCustomer.addAttribute("customer", customerRepository.show(id));
        return "customers/show";
    }

    @GetMapping("/new")
    public String newCustomer(@ModelAttribute("customer") Customer customer) {
        return "customers/new";
    }

    @PostMapping
    public String create(@ModelAttribute("customer") @Valid Customer customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "customers/new";

        customerRepository.save(customer);
        return "redirect:/customers";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("customer", customerRepository.show(id));
        return "customers/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("customer") @Valid Customer customer, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "customers/edit";

        customerRepository.update(id, customer);
        return "redirect:/customers";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        customerRepository.delete(id);
        return "redirect:/customers";
    }

}
