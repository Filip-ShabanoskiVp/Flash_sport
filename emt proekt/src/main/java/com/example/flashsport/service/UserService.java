package com.example.flashsport.service;


import com.example.flashsport.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService  extends UserDetailsService {
    User findById(String username);
    User registerUser(User user);
    List<User>findAllUsers();
    void deleteById(String username);
}
