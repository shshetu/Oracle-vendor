package com.shetu.bookstore.service;

import com.shetu.bookstore.domain.ShippingAddress;
import com.shetu.bookstore.domain.UserShipping;

public interface ShippingAddressService {
    ShippingAddress setByUserShipping(UserShipping userShipping, ShippingAddress shippingAddress);
}
