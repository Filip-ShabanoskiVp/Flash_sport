package com.example.flashsport.service.impl;

import com.example.flashsport.models.Manufacturer;
import com.example.flashsport.models.exceptions.ManufacturerNotFoundException;
import com.example.flashsport.repository.ManufacturerRepository;
import com.example.flashsport.service.ManufacturerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public List<Manufacturer> findAll() {
        return this.manufacturerRepository.findAll();
    }

    @Override
    public Manufacturer findById(Long id) {
        return this.manufacturerRepository.findById(id)
                .orElseThrow(()->new ManufacturerNotFoundException(id));
    }

    @Override
    public Manufacturer saveManufacturer(Manufacturer manufacturer) {
        return this.manufacturerRepository.save(manufacturer);
    }
}
