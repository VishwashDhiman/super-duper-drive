package com.udacity.jwdnd.course1.cloudstorage.entities;

import lombok.Data;

@Data
public class Credentials {
    public String userId;
    public String credentialId;
    public String url;
    public String userName;
    public String key;
    public String password;
    public String encodedPassword;
}
