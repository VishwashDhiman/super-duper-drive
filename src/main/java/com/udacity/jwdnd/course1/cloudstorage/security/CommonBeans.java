package com.udacity.jwdnd.course1.cloudstorage.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonBeans {
    @Bean
    public UserDetails strongEncryptor(){
        UserDetails encryptor = new UserDetails();
        return encryptor;
    }
}
