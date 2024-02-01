package com.example.crudapp.validator;

public interface Validator {
    void validate(Object object);  //using runtime exception which doesn't needs to be thrown
}
