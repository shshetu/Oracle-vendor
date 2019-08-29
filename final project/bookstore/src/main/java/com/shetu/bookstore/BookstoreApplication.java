package com.shetu.bookstore;

import com.shetu.bookstore.domain.User;
import com.shetu.bookstore.domain.security.Role;
import com.shetu.bookstore.domain.security.UserRole;
import com.shetu.bookstore.service.UserService;
import com.shetu.bookstore.utility.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class BookstoreApplication implements CommandLineRunner {
    @Autowired
    private UserService userService;
    public static void main(String[] args) {

        SpringApplication.run(BookstoreApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {

        //Create a new user
        User user1 = new User();
        user1.setFirstName("Mehnaz");
        user1.setLastName("Chowdhury");
        user1.setUsername("shanto");
        user1.setPassword(SecurityUtility.passwordEncoder().encode("1234"));
        user1.setEmail("shshetu2017@gmail.com");

        //create a role
        Set<UserRole> userRoles = new HashSet<>();
        Role role1 = new Role();
        role1.setRoleId(1);
        role1.setName("ROLE_USER");
        userRoles.add(new UserRole(user1,role1));

        //call the createUser() method to create a user
        userService.createUser(user1,userRoles);
    }

}
