package com.example.flashsport.service;

import com.example.flashsport.models.ShoppingCart;
import com.example.flashsport.models.dto.ChargeRequest;

public interface ShoppingCartService {
    ShoppingCart findActiveShoppingCartByUsername(String username);

    ShoppingCart createNewShoppingCart(String username);

    ShoppingCart addProductToShoppingCart(String username,Long productId);

    ShoppingCart removeProductFromShoppingCart(String username,Long productId);

    ShoppingCart getActiveShoppingCart(String username);

    ShoppingCart cancelActiveShoppingCart(String username);

    ShoppingCart checkoutShoppingCart(String username, ChargeRequest chargeRequest);
}
