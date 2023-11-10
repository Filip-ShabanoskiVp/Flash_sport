package com.example.flashsport.presentation;

import com.example.flashsport.models.User;
import com.example.flashsport.service.AuthService;
import com.example.flashsport.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user-profile")
public class UserController {

    private final AuthService authService;
    private final UserService userService;
    public UserController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @GetMapping
    public String addProductPage(Model model){
        User user = this.userService.findById(this.authService.getCurrentUserId());
        model.addAttribute("user",user);
        return "user-profile";
    }

}
