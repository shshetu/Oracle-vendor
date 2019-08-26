package com.shetu.bookstore.service.impl;

import com.shetu.bookstore.domain.UserPayment;
import com.shetu.bookstore.repository.UserPaymentRepository;
import com.shetu.bookstore.service.UserPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserPaymentServiceImpl implements UserPaymentService {
    @Autowired
    private UserPaymentRepository userPaymentRepository;
    @Override
    public UserPayment findById(Long id) {
        //create an object of the UsrPayment
        UserPayment userPayment = new UserPayment();

        //Make optonal: using bookRepository
        Optional<UserPayment> tempUserPayment = userPaymentRepository.findById(id);

        //check the logic and assign into userPayment object
        if (tempUserPayment.isPresent()){
            userPayment = tempUserPayment.get();
        }
        //return the userPayment
        return userPayment;

    }

    @Override
    public void removeById(Long id) {
        userPaymentRepository.deleteById(id);
    }
}
