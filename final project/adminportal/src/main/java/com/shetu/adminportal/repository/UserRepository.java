package com.shetu.adminportal.repository;

import com.shetu.adminportal.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    //define the methods
    User findByUsername(String username);

    User findByEmail(String email);
}
