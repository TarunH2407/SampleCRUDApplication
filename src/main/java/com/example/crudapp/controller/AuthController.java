package com.example.crudapp.controller;

import com.example.crudapp.service.AuthService;
import com.example.crudapp.model.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/addAuth")
    public ResponseEntity<Auth> addAuth(@RequestBody Auth auth)
    {
        Auth newAuth = authService.saveItem(auth);

        return new ResponseEntity<>(newAuth, HttpStatus.CREATED);
    }
}
