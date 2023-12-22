package com.martinps.entity;

import jakarta.persistence.Entity;

@Entity
public class Phone {

    private String number;
    private String cityCode;
    private String countryCode;
}
