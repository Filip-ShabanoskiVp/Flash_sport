package com.example.flashsport.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProductIsAlreadyInShoppingCart extends RuntimeException {
    public ProductIsAlreadyInShoppingCart(String name) {
        super(String.format("Product with name: %s is already in whopping cart",name));
    }
}
