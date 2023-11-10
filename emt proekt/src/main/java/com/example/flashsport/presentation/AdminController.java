package com.example.flashsport.presentation;

import com.example.flashsport.models.User;
import org.springframework.security.access.annotation.Secured;
import com.example.flashsport.service.AuthService;
import com.example.flashsport.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Secured("ROLE_ADMIN")
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;


    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getAdminPage(Model model){
        List<User> users= this.userService.findAllUsers();
        model.addAttribute("users",users);
        return "admin";
    }

    @PostMapping("/{username}/delete")
    public String deleteUser(@PathVariable String username){
        this.userService.deleteById(username);
        return "redirect:/admin";
    }
}
