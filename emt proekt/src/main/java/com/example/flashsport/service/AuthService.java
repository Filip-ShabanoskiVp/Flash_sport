package com.example.flashsport.service;

import com.example.flashsport.models.User;

public interface AuthService {
    User getCurrentUser();
    String getCurrentUserId();
    User signUpUser(String username, String password, String repeatedPassword,
                    String first_name,String last_name,
                    String email,String embg);
}
