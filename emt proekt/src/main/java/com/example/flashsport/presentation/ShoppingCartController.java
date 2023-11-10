package com.example.flashsport.presentation;

import com.example.flashsport.models.Product;
import com.example.flashsport.models.ShoppingCart;
import com.example.flashsport.models.dto.ChargeRequest;
import com.example.flashsport.service.AuthService;
import com.example.flashsport.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Secured("ROLE_USER")
@RequestMapping("/shopping-carts")
public class ShoppingCartController {

    @Value("${STRIPE_P_KEY}")
    private String publicKey;

    private final ShoppingCartService shoppingCartService;
    private final AuthService authService;

    public ShoppingCartController(ShoppingCartService shoppingCartService, AuthService authService) {
        this.shoppingCartService = shoppingCartService;
        this.authService = authService;
    }

    @GetMapping
    public String shoppingCartPage(Model model){
        try {
            ShoppingCart shoppingCart = this.shoppingCartService.
                    findActiveShoppingCartByUsername(this.authService.getCurrentUserId());
            model.addAttribute("shoppingCart",shoppingCart);
            model.addAttribute("currency","usd");
            model.addAttribute("amount",(int)(shoppingCart.getProducts().stream()
            .mapToDouble(Product::getCost).sum()*100));
            model.addAttribute("publicKey",this.publicKey);
            return "shopping-cart";
        }catch (RuntimeException ex){
            return "redirect:/products?error="+ex.getLocalizedMessage();
        }
    }

    @PostMapping
    public String checkout(ChargeRequest chargeRequest,Model model){
        try {
            ShoppingCart shoppingCart = this.shoppingCartService.checkoutShoppingCart(this.authService.getCurrentUserId(),
                    chargeRequest);
            return "redirect:/products?message=Successful Payment";
        }catch (RuntimeException ex){
            return "redirect:/shopping-carts?error="+ex.getLocalizedMessage();
        }
    }


    @PostMapping("/{productId}/add-product")
    public String addProductToShoppingCart(@PathVariable Long productId) {
        try {
            ShoppingCart shoppingCart = this.shoppingCartService.addProductToShoppingCart(this.authService.getCurrentUserId(), productId);
        } catch (RuntimeException ex) {
            return "redirect:/products?error=" + ex.getLocalizedMessage();
        }
        return "redirect:/products";
    }

    @PostMapping("/{productId}/remove-product")
    public String removeProductFromShoppingCart(@PathVariable Long productId){
        ShoppingCart shoppingCart = this.shoppingCartService.removeProductFromShoppingCart(this.authService.getCurrentUserId(),
                productId);
        return "redirect:/products";
    }

    @PostMapping("/cancel")
    public String cancelShoppingCart(){
        this.shoppingCartService.cancelActiveShoppingCart(this.authService.getCurrentUserId());
        return "redirect:/products";
    }
}
