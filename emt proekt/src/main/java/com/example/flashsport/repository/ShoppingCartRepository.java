package com.example.flashsport.repository;

import com.example.flashsport.models.ShoppingCart;
import com.example.flashsport.models.enumerations.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Long> {
    boolean existsByUserUsernameAndStatus(String username, CartStatus status);
    Optional<ShoppingCart> findByUserUsernameAndStatus(String username, CartStatus status);
}
