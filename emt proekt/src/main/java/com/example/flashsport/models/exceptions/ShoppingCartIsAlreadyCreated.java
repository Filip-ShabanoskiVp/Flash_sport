package com.example.flashsport.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
public class ShoppingCartIsAlreadyCreated extends RuntimeException {
    public ShoppingCartIsAlreadyCreated(String username) {
        super(String.format("Shopping cart with username %s is already created",username));
    }
}
