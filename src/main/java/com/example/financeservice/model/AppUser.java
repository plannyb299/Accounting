package com.example.financeservice.model;

import lombok.Data;

import javax.persistence.Column;
@Data
public class AppUser {
    private String firstName;
    private String lastName;
    private String email;
}
