package com.example.flashsport.repository;

import com.example.flashsport.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("select p.name,p.cost from Product as p")
    List<Object> getNameAndCost();
}
