package com.example.flashsport.presentation;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@PreAuthorize("!hasAnyRole('ROLE_ADMIN','ROLE_USE')")
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String LoginPage(@RequestParam(required = false)String info, Model model){
        model.addAttribute("info",info);
        return "login";
    }
}
