package ru.elagin.hibernate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.elagin.hibernate.dto.AutoFilter;
import ru.elagin.hibernate.models.Auto;
import ru.elagin.hibernate.response.Response;
import ru.elagin.hibernate.services.AutoService;

import javax.validation.Valid;

@Controller
@RequestMapping("/autos")
public class AutoController {

    private final AutoService autoService;

    @Autowired
    public AutoController(AutoService autoService) {
        this.autoService = autoService;
    }

    @GetMapping()
    public String index(Model model, @ModelAttribute("autoFilter") @Valid AutoFilter autoFilter, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "autos/index";
        Response response = autoService.index(autoFilter);
        String error = response.getError();
        if (error != null) {
            model.addAttribute("errorMessage", error);
            return "error";
        }
        model.addAttribute("autos", response.getObjects());
        return "autos/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        Response response = autoService.show(id);
        String error = response.getError();
        if (error != null) {
            model.addAttribute("errorMessage", error);
            return "error";
        }
        model.addAttribute("auto", response.getObject());
        return "autos/show";
    }

    @GetMapping("/new")
    public String newAuto(@ModelAttribute("auto") Auto auto) {
        return "autos/new";
    }

    @PostMapping
    public String create(@ModelAttribute("auto") @Valid Auto auto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors())
            return "autos/new";

        Response response = autoService.save(auto);
        String error = response.getError();
        if (error != null) {
            model.addAttribute("errorMessage", error);
            return "error";
        }
        return "redirect:/autos";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        Response response = autoService.show(id);
        String error = response.getError();
        if (error != null) {
            model.addAttribute("errorMessage", error);
            return "error";
        }
        model.addAttribute("auto", response.getObject());
        return "autos/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("auto") @Valid Auto auto, BindingResult bindingResult,
                         @PathVariable("id") int id, Model model) {
        if (bindingResult.hasErrors())
            return "autos/edit";

        Response response = autoService.update(id, auto);
        String error = response.getError();
        if (error != null) {
            model.addAttribute("errorMessage", error);
            return "error";
        }
        return "redirect:/autos";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id, Model model) {
        Response response = autoService.delete(id);
        String error = response.getError();
        if (error != null) {
            model.addAttribute("errorMessage", error);
            return "error";
        }
        return "redirect:/autos";
    }

}
