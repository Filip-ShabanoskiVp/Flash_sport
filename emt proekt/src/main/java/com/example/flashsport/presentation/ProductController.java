package com.example.flashsport.presentation;

import com.example.flashsport.models.Manufacturer;
import com.example.flashsport.models.Product;
import com.example.flashsport.service.AuthService;
import com.example.flashsport.service.ManufacturerService;
import com.example.flashsport.service.ProductService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ManufacturerService manufacturerService;

    public ProductController(ProductService productService,
                             ManufacturerService manufacturerService) {
        this.productService = productService;
        this.manufacturerService = manufacturerService;
    }


    @GetMapping
    public String productPage(Model model){
        List<Product>products = this.productService.findAll();
        model.addAttribute("products",products);
        return "products";
    }
    @GetMapping("/add-product")
    @Secured("ROLE_USER")
    public String addProductPage(Model model){
        List<Manufacturer> manufacturers = this.manufacturerService.findAll();
        model.addAttribute("manufacturers", manufacturers);
        model.addAttribute("product", new Product());
        return "add-product";
    }

    @PostMapping
    public String saveProduct(
            @Valid Product product,
            BindingResult bindingResult, @RequestParam MultipartFile image,
            Model model) {

        if (bindingResult.hasErrors()) {
            List<Manufacturer> manufacturers = this.manufacturerService.findAll();
            model.addAttribute("manufacturers", manufacturers);
            return "add-product";
        }
        try {
            this.productService.saveProduct(product, image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/products";
    }

    @PostMapping("/{id}/delete")
    @Secured("ROLE_USER")
    public String deleteProduct(@PathVariable Long id){
        this.productService.deleteById(id);
        return "redirect:/products";
    }

    @GetMapping("/{id}/edit")
    @Secured("ROLE_USER")
    public String editProductPage(Model model,@PathVariable Long id){
            Product product = this.productService.findAllById(id);
            List<Manufacturer>manufacturers = this.manufacturerService.findAll();
            model.addAttribute("product",product);
            model.addAttribute("manufacturers",manufacturers);
            return "edit-product";
    }


    @PostMapping("{id}/update")
    @Secured("ROLE_USER")
    public String updateProduct(
            @PathVariable Long id,
            @Valid Product product,
            BindingResult bindingResult, @RequestParam MultipartFile image,
            Model model) {

        if (bindingResult.hasErrors()) {
            List<Manufacturer> manufacturers = this.manufacturerService.findAll();
            model.addAttribute("manufacturers", manufacturers);
            return "edit-product";
        }
        try {
            this.productService.updateProduct(id,product,image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/products";
    }
}
