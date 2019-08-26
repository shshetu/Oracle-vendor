package com.shetu.bookstore.utility;

import com.shetu.bookstore.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MailConstructor {
    //comes from spring core
    @Autowired
    private Environment environment;
    //SimpleMailMessage type method
    //required: contextPath,lcoale data, time language, token, user, password
    public SimpleMailMessage constructResetTokenEmail(
            String contextPath, Locale locale, String token, User user,String password
            ){
     //add a url
        //add the contextPath with the controller mapping
        String url = contextPath+"/newUser?token="+token;
   //add a message
    String message = "\nPlease click on this link to verify your email" +
            "and edit personal information.Your password is: \n"+password;
//create an object of SimpleMailMessage
        SimpleMailMessage email = new SimpleMailMessage();
        //set the user email
        email.setTo(user.getEmail());
        //set the email subject
        email.setSubject("Shetu's Bookstore - New User");
        //set the text : url + message
        email.setText(url+message);
        //sent from which email
        email.setFrom(environment.getProperty("support.email"));
    return email;
    }
}
