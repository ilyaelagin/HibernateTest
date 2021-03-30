package ru.elagin.hibernate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.elagin.hibernate.dto.CustomerFilter;
import ru.elagin.hibernate.models.Customer;
import ru.elagin.hibernate.response.Response;
import ru.elagin.hibernate.services.CustomerService;

import javax.validation.Valid;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping()
    public String index(Model model, @ModelAttribute("customerFilter") @Valid CustomerFilter customerFilter, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "customers/index";
        Response response = customerService.index(customerFilter);
        String error = response.getError();
        if (error != null) {
            model.addAttribute("errorMessage", error);
            return "error";
        }
        model.addAttribute("customers", response.getObjects());
        return "customers/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        Response response = customerService.show(id);
        String error = response.getError();
        if (error != null) {
            model.addAttribute("errorMessage", error);
            return "error";
        }
        model.addAttribute("customer", response.getObject());
        return "customers/show";
    }

    @GetMapping("/new")
    public String newCustomer(@ModelAttribute("customer") Customer customer) {
        return "customers/new";
    }

    @PostMapping
    public String create(@ModelAttribute("customer") @Valid Customer customer, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors())
            return "customers/new";

        Response response = customerService.save(customer);
        String error =  response.getError();
        if (error != null) {
            model.addAttribute("errorMessage", error);
            return "error";
        }
        return "redirect:/customers";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        Response response = customerService.show(id);
        String error = response.getError();
        if (error != null) {
            model.addAttribute("errorMessage", error);
            return "error";
        }
        model.addAttribute("customer", response.getObject());
        return "customers/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("customer") @Valid Customer customer, BindingResult bindingResult,
                         @PathVariable("id") int id, Model model) {
        if (bindingResult.hasErrors())
            return "customers/edit";

        Response response = customerService.update(id, customer);
        String error = response.getError();
        if (error != null) {
            model.addAttribute("errorMessage", error);
            return "error";
        }
        return "redirect:/customers";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id, Model model) {
        Response response = customerService.delete(id);
        String error = response.getError();
        if (error != null) {
            model.addAttribute("errorMessage", error);
            return "error";
        }
        return "redirect:/customers";
    }

}
