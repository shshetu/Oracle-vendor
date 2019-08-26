package com.shetu.adminportal.service.impl;

import com.shetu.adminportal.domain.User;
import com.shetu.adminportal.domain.security.PasswordResetToken;
import com.shetu.adminportal.domain.security.UserRole;
import com.shetu.adminportal.repository.PasswordResetTokenRepository;
import com.shetu.adminportal.repository.RoleRepository;
import com.shetu.adminportal.repository.UserRepository;
import com.shetu.adminportal.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
public class UserServiceImpl implements UserService {
//Logger
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
    //Field Injection 3 respository
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

        //Lot of work to create a User
    @Override
    public User createUser(User user, Set<UserRole> userRoles) throws Exception {
        //find the username at first
        //then add all the informations
        User localUser = userRepository.findByUsername(user.getUsername());
        if (localUser!=null){
            LOG.info("user {} already exists. Nothing will be done.",user.getUsername());
        }else {
            //prepare the userRoles at first
            for (UserRole ur:userRoles){
                //save role using role repository
                roleRepository.save(ur.getRole());
            }
            //add the userroles to the user
            user.getUserRoles().addAll(userRoles);
            //then add all the user and userdetails inforamation to the localUser
            localUser = userRepository.save(user);
        }

        return localUser;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
