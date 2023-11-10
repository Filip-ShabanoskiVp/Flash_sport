package com.example.flashsport.service;

import com.example.flashsport.models.Manufacturer;
import com.example.flashsport.models.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product findAllById(Long id);

    void deleteById(Long id);

    Product saveProduct(Product product, MultipartFile image) throws IOException;

    Product updateProduct(Long id, Product product, MultipartFile image) throws IOException;
}
