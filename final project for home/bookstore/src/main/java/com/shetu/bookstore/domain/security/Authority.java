package com.shetu.bookstore.domain.security;

import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority {
   //create a final String
    private final String authority;

    //constructor
    public Authority(String authority){
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
    return authority;
    }
}
