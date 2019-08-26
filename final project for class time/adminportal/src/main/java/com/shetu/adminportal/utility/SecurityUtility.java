package com.shetu.adminportal.utility;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class SecurityUtility {
    //create a final string salt
    private static final String SALT = "salt"; //salt should be protected carefully

    //Create 2 beans:
    //Bean-1: Password encoder
    @Bean
    public static BCryptPasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder(12,new SecureRandom(SALT.getBytes()));
}

    //Bean-2 : random password generator
    @Bean
    public static String randomPassword(){
        //How many characters are used
        String SALTCHARS ="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        //use string builder
        StringBuilder salt = new StringBuilder();
        //create a random class
        Random random  = new Random();

        //use the logic
        while (salt.length()<18){
            int index = (int) (random.nextFloat()*SALTCHARS.length());
            //append the character with the salt object
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }
}
