package com.shetu.adminportal.repository;

import com.shetu.adminportal.domain.security.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role,Long> {
    //role type data
    Role findByname(String name);
}
