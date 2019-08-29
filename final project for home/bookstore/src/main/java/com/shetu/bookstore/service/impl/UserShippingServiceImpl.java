package com.shetu.bookstore.service.impl;

import com.shetu.bookstore.domain.User;
import com.shetu.bookstore.domain.UserShipping;
import com.shetu.bookstore.repository.UserShippingRepository;
import com.shetu.bookstore.service.UserShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class UserShippingServiceImpl implements UserShippingService {
    @Autowired
    private UserShippingRepository userShippingRepository;

    @Override
    public UserShipping findById(Long id) {
        UserShipping userShipping = new UserShipping();

        Optional<UserShipping> tempUserShipping = userShippingRepository.findById(id);

        if(tempUserShipping.isPresent()){
            userShipping = tempUserShipping.get();
        }
        return userShipping;
    }

    @Override
    public void removeById(Long id) {
        userShippingRepository.deleteById(id);
    }
}
