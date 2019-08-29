package com.shetu.bookstore.service;

import com.shetu.bookstore.domain.BillingAddress;
import com.shetu.bookstore.domain.UserBilling;

public interface BillingAddressService {
    BillingAddress setByUserBilling(UserBilling userBilling,BillingAddress billingAddress);
}
