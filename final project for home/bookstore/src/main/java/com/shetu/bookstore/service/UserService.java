package com.shetu.bookstore.service;

import com.shetu.bookstore.domain.User;
import com.shetu.bookstore.domain.UserBilling;
import com.shetu.bookstore.domain.UserPayment;
import com.shetu.bookstore.domain.UserShipping;
import com.shetu.bookstore.domain.security.PasswordResetToken;
import com.shetu.bookstore.domain.security.UserRole;

import java.util.Set;

//methods are declared explicitly
public interface UserService {
    //Random Password token generator
    PasswordResetToken getPasswordResetToken(final String token);
    //reset it by using: user and token
    void createPasswordResetTokenForUser(final User user, final String token);

    //Crud : User class
    //findby
    User findByUsername(String username);
    User findByEmail(String email);

    //create: useing user, and userrole
    User createUser(User user, Set<UserRole> userRoles) throws Exception;
    //save: by user
    User save(User user);

    ///update
    void updateUserBilling(UserBilling userBilling, UserPayment userPayment, User user);

    //setDefaultPayment
    void setUserDefaultPayment(Long userPaymentId, User user);

    //update User service
    void updateUserShipping(UserShipping userShipping, User user);

    //default user service
    void setUserDefaultShipping(Long userShippingId,User user);

}
