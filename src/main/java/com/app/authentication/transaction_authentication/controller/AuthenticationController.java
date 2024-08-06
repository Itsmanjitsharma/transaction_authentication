package com.app.authentication.transaction_authentication.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class AuthenticationController {
    
    @Autowired
    UserService userService;

    @Autowired
    JwtUtil  jwtUtil;


    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    @PostMapping("/token")
    public ResponseEntity<String> getToken(@RequestBody SignIn signin){
                logger.info("request recieved to get logger from user {}",signin.getUsername());
                 User user = userService.getUser_Details(signin);
                 if(user!=null){
                  logger.info("token is generated for user", user.getUsername());
                          return new ResponseEntity<>(jwtUtil.generateToken(user),HttpStatus.OK);
                 }else{
                  logger.info("invalid credential to generate toke for user", signin.getUsername());
                    return new ResponseEntity<>("Invalid Credential",HttpStatus.UNAUTHORIZED);
                 }
      }


}
