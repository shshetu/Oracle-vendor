package com.shetu.adminportal.service;


import com.shetu.adminportal.domain.User;
import com.shetu.adminportal.domain.security.PasswordResetToken;
import com.shetu.adminportal.domain.security.UserRole;

import java.util.Set;

//methods are declared explicitly
public interface UserService {
    //create: useing user, and userrole
    User createUser(User user, Set<UserRole> userRoles) throws Exception;
    //save: by user
    User save(User user);

}
