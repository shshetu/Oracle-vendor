package com.shetu.adminportal.domain.security;


import com.shetu.adminportal.domain.User;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
public class PasswordResetToken {
    //create a final string : EXPIRATION
    private static final int EXPIRATION = 60 * 24;
    //Password expiry date
    private Date expiryDate;

    //Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //token
    private String token;

    //Object Relation: User
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    private User user;

    //create 2 methods
    //method-1 : calculated expiry date
    private Date calculateExpiryDate(final int expiryTimeInMinutes){
    // Calender instance
        final Calendar cal = Calendar.getInstance();
        ///get the current time and set it into the cal object
        cal.setTimeInMillis(new Date().getTime());
        //add the initializing time and finishing time
        cal.add(Calendar.MINUTE,expiryTimeInMinutes);
        //get the time from the cal object
        return new Date(cal.getTime().getTime());
    }

    //method-2: update Token
    public void updateToken(final String token){
        this.token = token;
        //calculate the expirydate using the calculateExpiryDate method
        this.expiryDate = calculateExpiryDate(EXPIRATION);

    }


    //constructor

    public PasswordResetToken() {
    }

    public PasswordResetToken(String token, User user) {
        this.token = token;
        this.user = user;
    }
//getter and setter

    public static int getEXPIRATION() {
        return EXPIRATION;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



    //toString

    @Override
    public String toString() {
        return "PasswordResetToken{" +
                "expiryDate=" + expiryDate +
                ", id=" + id +
                ", token='" + token + '\'' +
                ", user=" + user +
                '}';
    }
}
