package com.example.crudapp.service;

import com.example.crudapp.model.Auth;
import com.example.crudapp.repo.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AuthRepository authRepository;

    public boolean isValidAuthToken(String authToken) {
        Optional<Auth> authOptional = authRepository.findById(authToken);
        return authOptional.isPresent();
    }

    public Auth saveItem(Auth auth) {
        return authRepository.save(auth);
    }
}

