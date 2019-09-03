package com.shetu.adminportal;

import com.shetu.adminportal.domain.User;
import com.shetu.adminportal.domain.security.Role;
import com.shetu.adminportal.domain.security.UserRole;
import com.shetu.adminportal.service.UserService;
import com.shetu.adminportal.utility.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.mail.search.SearchTerm;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class AdminportalApplication implements CommandLineRunner {
@Autowired
private UserService userService;
    public static void main(String[] args) {
        SpringApplication.run(AdminportalApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    //Create a new user
        User user1 = new User();
        user1.setFirstName("Shetu");
        user1.setLastName("Shahariar");
        user1.setUsername("admin");
        user1.setPassword(SecurityUtility.passwordEncoder().encode("admin"));
        user1.setEmail("admin@gmail.com");

        //create a role
        Set<UserRole> userRoles = new HashSet<>();
        Role role1 = new Role();
        role1.setRoleId(0);
        role1.setName("ROLE_ADMIN");
        userRoles.add(new UserRole(user1,role1));

        //call the createUser() method to create a user
        userService.createUser(user1,userRoles);

    }
}
