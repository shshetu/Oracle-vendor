package com.shetu.bookstore.repository;

import com.shetu.bookstore.domain.UserPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPaymentRepository extends JpaRepository<UserPayment,Long> {
}
