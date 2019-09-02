package com.shetu.bookstore.utility;

import com.shetu.bookstore.domain.Order;
import com.shetu.bookstore.domain.User;
import org.hibernate.sql.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

@Component
public class MailConstructor {
    //comes from spring core
    @Autowired
    private Environment environment;

    @Autowired
    private TemplateEngine templateEngine;

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

    //////////order confirmation
    public MimeMessagePreparator constructOrderConfirmationEmail(User user, Order order,Locale locale){
        //set the context
        Context context = new Context();
        context.setVariable("order",order);
        context.setVariable("user",user);
        context.setVariable("cartItemList",order.getCartItemList());
        String text = templateEngine.process("orderConfirmationEmailTemplate",context);

        MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper email = new MimeMessageHelper(mimeMessage);
                email.setTo(user.getEmail());
                email.setSubject("order Confirmation - "+order.getId());
                email.setText(text,true);
                email.setFrom(new InternetAddress("alusada365@gmail.com"));
            }
        };
        return messagePreparator;
    }
}
