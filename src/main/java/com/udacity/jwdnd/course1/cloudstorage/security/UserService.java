package com.udacity.jwdnd.course1.cloudstorage.security;

import com.udacity.jwdnd.course1.cloudstorage.entities.User;
import com.udacity.jwdnd.course1.cloudstorage.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService{
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            System.out.println("LOGGEDDD INNN");
            return org.springframework.security.core.userdetails.User.withUsername(user.getUserid()).password(user.getPassword()).authorities("role").build();
        }
        System.out.println("LOGGEDDD INNN ERROR");
        throw new UsernameNotFoundException("User has not registered yet");

    }
}
