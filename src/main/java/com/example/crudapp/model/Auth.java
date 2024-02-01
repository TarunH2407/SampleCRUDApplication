package com.example.crudapp.model;


import lombok.*;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
public class Auth {

    @Id
    private String authToken;

    private String secret;

    // Getters and setters
}
