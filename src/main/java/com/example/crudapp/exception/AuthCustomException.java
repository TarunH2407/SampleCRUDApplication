package com.example.crudapp.exception;

public class AuthCustomException extends RuntimeException {
    public AuthCustomException(String message) {
        super(message);
    }
}
