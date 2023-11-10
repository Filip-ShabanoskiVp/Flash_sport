package com.example.flashsport.service.impl;

import com.example.flashsport.models.Product;
import com.example.flashsport.models.ShoppingCart;
import com.example.flashsport.models.User;
import com.example.flashsport.models.dto.ChargeRequest;
import com.example.flashsport.models.enumerations.CartStatus;
import com.example.flashsport.models.exceptions.*;
import com.example.flashsport.repository.ShoppingCartRepository;
import com.example.flashsport.service.PaymentService;
import com.example.flashsport.service.ProductService;
import com.example.flashsport.service.ShoppingCartService;
import com.example.flashsport.service.UserService;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ProductService productService;
    private final UserService userService;
    private final PaymentService paymentService;

    private final ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartServiceImpl(ProductService productService, UserService userService, PaymentService paymentService, ShoppingCartRepository shoppingCartRepository) {
        this.productService = productService;
        this.userService = userService;
        this.paymentService = paymentService;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Override
    public ShoppingCart findActiveShoppingCartByUsername(String username) {
        return this.shoppingCartRepository.findByUserUsernameAndStatus(username, CartStatus.Created)
                .orElseThrow(()->new ShoppingCartIsNotActiveException(username));
    }

    @Override
    public ShoppingCart createNewShoppingCart(String username) {
        User user = this.userService.findById(username);
        if(this.shoppingCartRepository.existsByUserUsernameAndStatus(user.getUsername(),CartStatus.Created)){
            throw new ShoppingCartIsAlreadyCreated(username);
        }
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCart addProductToShoppingCart(String username, Long productId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);
        Product product =  this.productService.findAllById(productId);
        for(Product p : shoppingCart.getProducts()){
            if(p.getId().equals(productId)){
                throw new ProductIsAlreadyInShoppingCart(product.getName());
            }
        }
        shoppingCart.getProducts().add(product);
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCart removeProductFromShoppingCart(String username, Long productId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);
        shoppingCart.setProducts(shoppingCart.getProducts().stream().filter(product -> !product.getId().equals(productId))
        .collect(Collectors.toList()));
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart getActiveShoppingCart(String username) {
        return this.shoppingCartRepository.findByUserUsernameAndStatus(username,CartStatus.Created)
                .orElseGet(()->{
                    ShoppingCart shoppingCart = new ShoppingCart();
                    User user = this.userService.findById(username);
                    shoppingCart.setUser(user);
                    return this.shoppingCartRepository.save(shoppingCart);
                });
    }

    @Override
    public ShoppingCart cancelActiveShoppingCart(String username) {
        ShoppingCart shoppingCart = this.shoppingCartRepository
                .findByUserUsernameAndStatus(username,CartStatus.Created)
                .orElseThrow(()->new ShoppingCartIsNotActiveException(username));
        shoppingCart.setStatus(CartStatus.Canceled);
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCart checkoutShoppingCart(String username, ChargeRequest chargeRequest) {
        ShoppingCart shoppingCart = this.shoppingCartRepository
                .findByUserUsernameAndStatus(username,CartStatus.Created)
                .orElseThrow(()->new ShoppingCartIsNotActiveException(username));
        List<Product>products = shoppingCart.getProducts();
        float price=0;

        for (Product p:products){
            if(p.getQuantity()<=0){
                throw new ProductOutOfStockException(p.getName());
            }
            p.setQuantity(p.getQuantity()-1);
            price+=p.getCost();
        }
        Charge charge = null;
        try {
            charge = this.paymentService.pay(chargeRequest);
        } catch (AuthenticationException | InvalidRequestException | APIConnectionException
                | APIException | CardException  e ) {
            throw new TransactionalFailedException(username,e.getMessage());
        }
        shoppingCart.setProducts(products);
        shoppingCart.setStatus(CartStatus.Finished);
        return this.shoppingCartRepository.save(shoppingCart);
    }
}
