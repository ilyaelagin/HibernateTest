package ru.elagin.hibernate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.elagin.hibernate.dto.AutoFilter;
import ru.elagin.hibernate.models.Auto;
import ru.elagin.hibernate.repository.AutoRepository;

import javax.validation.Valid;

@Controller
@RequestMapping("/autos")
public class AutosController {

    private final AutoRepository autoRepository;

    @Autowired
    public AutosController(AutoRepository autoRepository) {
        this.autoRepository = autoRepository;
    }

    @GetMapping()
    public String index(Model model, @ModelAttribute("autoFilter") @Valid AutoFilter autoFilter, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "autos/index";
        model.addAttribute("autos", autoRepository.index(autoFilter));
        return "autos/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("auto", autoRepository.show(id));
        return "autos/show";
    }

    @GetMapping("/new")
    public String newAuto(@ModelAttribute("auto") Auto auto) {
        return "autos/new";
    }

    @PostMapping
    public String create(@ModelAttribute("auto") @Valid Auto auto, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "autos/new";

        autoRepository.save(auto);
        return "redirect:/autos";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("auto", autoRepository.show(id));
        return "autos/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("auto") @Valid Auto auto, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "autos/edit";

        autoRepository.update(id, auto);
        return "redirect:/autos";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        autoRepository.delete(id);
        return "redirect:/autos";
    }

}
