package com.app.authentication.transaction_authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.authentication.transaction_authentication.config.JwtUtil;
import com.app.authentication.transaction_authentication.entity.SignIn;
import com.app.authentication.transaction_authentication.entity.User;
import com.app.authentication.transaction_authentication.service.UserService;

@RestController
public class AuthenticationController {
    
    @Autowired
    UserService userService;

    @Autowired
    JwtUtil  jwtUtil;

    @PostMapping("/token")
    public ResponseEntity<String> getToken(@RequestBody SignIn signin){
                 User user = userService.getUser_Details(signin);
                 if(user!=null){
                          return new ResponseEntity<>(jwtUtil.generateToken(user),HttpStatus.OK);
                 }else{
                    return new ResponseEntity<>("Invalid Credential",HttpStatus.UNAUTHORIZED);
                 }
      }


}
