package com.example.flashsport.service;

import com.example.flashsport.models.Manufacturer;

import java.util.List;

public interface ManufacturerService {

    List<Manufacturer> findAll();

    Manufacturer findById(Long id);

    Manufacturer saveManufacturer(Manufacturer manufacturer);
}
