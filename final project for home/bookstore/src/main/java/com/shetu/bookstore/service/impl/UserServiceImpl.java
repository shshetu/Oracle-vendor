package com.shetu.bookstore.service.impl;

import com.shetu.bookstore.domain.*;
import com.shetu.bookstore.domain.security.PasswordResetToken;
import com.shetu.bookstore.domain.security.UserRole;
import com.shetu.bookstore.repository.*;
import com.shetu.bookstore.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
public class UserServiceImpl implements UserService {
//Logger
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
    //Field Injection 3 respository
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private UserPaymentRepository userPaymentRepository;

    @Autowired
    UserShippingRepository userShippingRepository;
    @Override
    public PasswordResetToken getPasswordResetToken(final String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    @Override
    public void createPasswordResetTokenForUser(final User user, final String token) {
        //since we are passing data useing two objects
        final PasswordResetToken myToken = new PasswordResetToken(token,user);
        //since we want to create
    passwordResetTokenRepository.save(myToken);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
        //Lot of work to create a User
    @Override
    @Transactional
    public User createUser(User user, Set<UserRole> userRoles) throws Exception {
        //find the username at first
        //then add all the informations
        User localUser = userRepository.findByUsername(user.getUsername());
        if (localUser!=null){
            LOG.info("user {} already exists. Nothing will be done.",user.getUsername());
        }else {
            //prepare the userRoles at first
            for (UserRole ur:userRoles){
                //save role using role repository
                roleRepository.save(ur.getRole());
            }
            //add the userroles to the user
            user.getUserRoles().addAll(userRoles);

            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setUser(user);
            user.setShoppingCart(shoppingCart);

            user.setUserShippingList(new ArrayList<UserShipping>());
            user.setUserPaymentList(new ArrayList<UserPayment>());
            //then add all the user and userdetails inforamation to the localUser
            localUser = userRepository.save(user);
        }

        return localUser;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
    //update
    @Override
    public void updateUserBilling(UserBilling userBilling, UserPayment userPayment, User user) {
        userPayment.setUser(user);
        userPayment.setUserBilling(userBilling);
        userPayment.setDefaultPayment(true);
        userBilling.setUserPayment(userPayment);
        user.getUserPaymentList().add(userPayment);
        save(user);
    }

    @Override
    public void setUserDefaultPayment(Long userPaymentId, User user) {
        List<UserPayment> userPaymentList = userPaymentRepository.findAll();

        for (UserPayment userPayment: userPaymentList){
            if(userPayment.getId() == userPaymentId){
                userPayment.setDefaultPayment(true);
                userPaymentRepository.save(userPayment);
            }else{
                userPayment.setDefaultPayment(false);
                userPaymentRepository.save(userPayment);
            }
        }
    }

    @Override
    public void updateUserShipping(UserShipping userShipping, User user) {
    userShipping.setUser(user);
    userShipping.setUserShippingDefault(true);
    user.getUserShippingList().add(userShipping);
    save(user);
    }

    @Override
    public void setUserDefaultShipping(Long userShippingId, User user) {

        List<UserShipping> userShippingList = userShippingRepository.findAll();

        for (UserShipping userShipping: userShippingList){
            if(userShipping.getId() == userShippingId){
                userShipping.setUserShippingDefault(true);
                userShippingRepository.save(userShipping);
            }else{
                userShipping.setUserShippingDefault(false);
                userShippingRepository.save(userShipping);
            }
        }

    }
}
