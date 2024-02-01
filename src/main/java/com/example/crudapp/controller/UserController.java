package com.example.crudapp.controller;

import com.example.crudapp.exception.AuthCustomException;
import com.example.crudapp.model.Item;
import com.example.crudapp.model.User;
import com.example.crudapp.service.ItemService;
import com.example.crudapp.service.UserService;
import com.example.crudapp.validator.AuthValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthValidator authValidator;

    @PostMapping("/addUser")
    public ResponseEntity<User> addItem(@RequestBody User user, @RequestHeader("Auth") String authToken) {

        try{
            authValidator.validate(authToken);
            User newUser = userService.saveUser(user);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (AuthCustomException ae) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
