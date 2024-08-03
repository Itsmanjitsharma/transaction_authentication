package com.app.authentication.transaction_authentication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.authentication.transaction_authentication.entity.SignIn;
import com.app.authentication.transaction_authentication.entity.User;
import com.app.authentication.transaction_authentication.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    UserRepository userRepository;
    public User getUser_Details(SignIn signIn){
       return userRepository.findByUsernameAndPassword(signIn.getUsername(),signIn.getPassword());
    }

}
