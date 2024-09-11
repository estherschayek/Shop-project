package com.example.shop_project.controlers;

import com.example.shop_project.exception.CustomException;
import com.example.shop_project.jwt.AuthenticationRequest;
import com.example.shop_project.login.LoginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public abstract class ClientController {

    @Autowired
    LoginManager loginManager;

    public abstract ResponseEntity<?> login(AuthenticationRequest authenticationRequest) throws CustomException;
}
