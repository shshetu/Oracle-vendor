package com.shetu.bookstore.service.impl;

import com.shetu.bookstore.domain.BillingAddress;
import com.shetu.bookstore.domain.UserBilling;
import com.shetu.bookstore.service.BillingAddressService;
import org.springframework.stereotype.Service;

@Service
public class BillingAddressServiceImpl implements BillingAddressService {
    @Override
    public BillingAddress setByUserBilling(UserBilling userBilling, BillingAddress billingAddress) {
        billingAddress.setBillingAddressName(userBilling.getUserBillingName());
        billingAddress.setBillingAddressStreet1(userBilling.getUserBillingStreet1());
        billingAddress.setBillingAddressStreet2(userBilling.getUserBillingStreet2());
        billingAddress.setBillingAddressCity(userBilling.getUserBillingCity());
        billingAddress.setBillingAddressState(userBilling.getUserBillingState());
        billingAddress.setBillingAddressCountry(userBilling.getUserBillingCountry());
        billingAddress.setBillingAddressZipcode(userBilling.getUserBillingZipcode());
        return billingAddress;
    }
}
