package com.shetu.bookstore.service.impl;

import com.shetu.bookstore.domain.User;
import com.shetu.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//implements the core Security feature
@Service
public class UserSecurityService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //find the object of user by username
        User user = userRepository.findByUsername(username);
    if(null == user){
        throw new UsernameNotFoundException("Username not found");
    }
        return user;
    }
}
