package com.example.UserServiceApp.security.repository.services;


import com.example.UserServiceApp.model.User;
import com.example.UserServiceApp.repository.UserRepository;
import com.example.UserServiceApp.security.model.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmailID(username);
        if(user.isEmpty()){
            throw new UsernameNotFoundException("User not found with this Email Id");
        }
        return new CustomUserDetails(user.get());
    }
}
