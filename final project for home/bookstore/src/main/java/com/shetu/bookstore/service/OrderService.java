package com.shetu.bookstore.service;

import com.shetu.bookstore.domain.*;

public interface OrderService {
    Order createOrder(ShoppingCart shoppingCart, ShippingAddress shippingAddress,
                      BillingAddress billingAddress, Payment payment,String shippingMethod,User user);
    Order findById(Long id);
}
