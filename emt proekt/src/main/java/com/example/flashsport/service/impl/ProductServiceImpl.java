package com.example.flashsport.service.impl;

import com.example.flashsport.models.Manufacturer;
import com.example.flashsport.models.Product;
import com.example.flashsport.models.exceptions.ProductNotFoundException;
import com.example.flashsport.repository.ProductRepository;
import com.example.flashsport.service.ManufacturerService;
import com.example.flashsport.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ManufacturerService manufacturerService;

    public ProductServiceImpl(ProductRepository productRepository, ManufacturerService manufacturerService) {
        this.productRepository = productRepository;
        this.manufacturerService = manufacturerService;
    }

    @Override
    public List<Product> findAll() {
        return this.productRepository.findAll();
    }

    @Override
    public Product findAllById(Long id) {
        return this.productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException(id));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        this.productRepository.deleteById(id);
    }

    @Override
    public Product saveProduct(Product product, MultipartFile image) throws IOException {
        Manufacturer manufacturer = this.manufacturerService.findById(product.getManufacturer().getId());
        product.setManufacturer(manufacturer);
        if(image!=null && !image.getName().isEmpty()){
            byte[] bytes = image.getBytes();
            String imageInBase64 = String.format("data:%s;base64,%s",image.getContentType(),
                    Base64.getEncoder().encodeToString(bytes));

            product.setProductImage(imageInBase64);
        }
        return this.productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product product, MultipartFile image) throws IOException {
        Product p = this.findAllById(id);
        Manufacturer manufacturer = this.manufacturerService.findById(product.getManufacturer().getId());
        p.setManufacturer(manufacturer);
        p.setCost(product.getCost());
        p.setQuantity(product.getQuantity());
        if (image != null && !image.getName().isEmpty()) {
            byte[] bytes = image.getBytes();
            String base64Image = String.format("data:%s;base64,%s", image.getContentType(), Base64.getEncoder().encodeToString(bytes));
            p.setProductImage(base64Image);
        }
        return this.productRepository.save(p);
    }


}
