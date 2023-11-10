package com.example.flashsport.presentation;

import com.example.flashsport.models.Product;
import com.example.flashsport.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("/statistics")
public class StatisticsController {
    private final ProductRepository productRepository;

    public StatisticsController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public String getStatisticsPage(Model model){
//        Map<String,Integer>map = new LinkedHashMap<>();
//        map.put("Java",40);
//        map.put("Dev oops",25);
//        map.put("Python",20);
//        map.put(".Net",15);
//        model.addAttribute("map",map);
        List<Product>products = this.productRepository.findAll();
        Map<String,Float>productData = new HashMap<>();
        for (Product p : products){
                float cost = p.getCost();
                productData.put(p.getName(),cost);
        }
        model.addAttribute("productData",productData);
        Map<String,Integer>productPieData = new HashMap<>();
        for (Product p : products){
            int quantity = p.getQuantity();
            productPieData.put(p.getName(),quantity);
        }
        model.addAttribute("productPieData",productPieData);
        return "statistics";
    }
}
