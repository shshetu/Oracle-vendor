package com.shetu.bookstore.service;

import com.shetu.bookstore.domain.Payment;
import com.shetu.bookstore.domain.UserPayment;

public interface PaymentService {
Payment setByUserPayment(UserPayment userPayment, Payment payment);
}
