package com.shetu.bookstore.repository;

import com.shetu.bookstore.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    //define the methods
    User findByUsername(String username);
    User findByEmail(String email);
}
