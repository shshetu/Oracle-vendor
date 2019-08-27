package com.shetu.bookstore.service;

import com.shetu.bookstore.domain.UserPayment;

public interface UserPaymentService {
    UserPayment findById(Long id);
    void removeById(Long id);
}
