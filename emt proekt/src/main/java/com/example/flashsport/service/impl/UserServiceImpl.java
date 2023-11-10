package com.example.flashsport.service.impl;

import com.example.flashsport.models.User;
import com.example.flashsport.models.exceptions.UserAlreadyExistsException;
import com.example.flashsport.models.exceptions.UserNotFoundException;
import com.example.flashsport.repository.RoleRepository;
import com.example.flashsport.repository.ShoppingCartRepository;
import com.example.flashsport.repository.UserRepository;
import com.example.flashsport.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;


    public UserServiceImpl(UserRepository userRepository, ShoppingCartRepository shoppingCartRepository) {
        this.userRepository = userRepository;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Override
    public User findById(String username) {
        return this.userRepository.findById(username)
                .orElseThrow(()->new UserNotFoundException(username));
    }

    @Override
    public User registerUser(User user) {
        if(this.userRepository.existsById(user.getUsername())){
            throw new UserAlreadyExistsException(user.getUsername());
        }
        return this.userRepository.save(user);
    }

    @Override
    public List<User> findAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public void deleteById(String username) {
        User user = this.findById(username);
        user.setRoles(null);
        this.userRepository.deleteById(username);
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.userRepository.findById(s)
                .orElseThrow(()->new UsernameNotFoundException(s));
    }
}
