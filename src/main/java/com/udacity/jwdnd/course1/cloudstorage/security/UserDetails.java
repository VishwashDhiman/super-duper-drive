package com.udacity.jwdnd.course1.cloudstorage.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class UserDetails{
    private String name;

//    public UserDetails() {
//        this.name = name;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}