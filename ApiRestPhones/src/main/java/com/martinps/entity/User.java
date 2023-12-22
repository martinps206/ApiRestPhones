package com.martinps.entity;

import jakarta.persistence.Entity;

@Entity
public class User {

    private String name;
    private String email;
    private String password;
    private Phone phones;
}
