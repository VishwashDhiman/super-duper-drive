package com.udacity.jwdnd.course1.cloudstorage.entities;

import lombok.Data;

@Data
public class User {
    public String userid;
    public String username;
    public String password;
    public String salt;
    public String firstname;
    public String lastname;
}
