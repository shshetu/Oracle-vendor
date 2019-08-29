package com.shetu.bookstore.service;

import com.shetu.bookstore.domain.UserShipping;

public interface UserShippingService {
    UserShipping findById(Long id);
    void removeById(Long id);
}
