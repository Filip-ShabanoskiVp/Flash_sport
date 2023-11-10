package com.example.flashsport.presentation;

import com.example.flashsport.models.Manufacturer;
import com.example.flashsport.service.ManufacturerService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@Secured("ROLE_USER")
@RequestMapping("/add-manufacturer")
public class ManufacturerController {

    private final ManufacturerService manufacturerService;

    public ManufacturerController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @GetMapping
    public String addManufacturerPage(Model model){
        model.addAttribute("manufacturer",new Manufacturer());
        return "add-manufacturer";
    }

    @PostMapping
    public String saveManufacturer(@Valid Manufacturer manufacturer){
        this.manufacturerService.saveManufacturer(manufacturer);
        return "redirect:/products/add-product";
    }
}
