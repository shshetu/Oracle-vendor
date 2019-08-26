package com.shetu.bookstore.repository;

import com.shetu.bookstore.domain.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    //role type data
    Role findByname(String name);
}
