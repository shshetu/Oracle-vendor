package com.shetu.bookstore.domain.security;

import com.shetu.bookstore.domain.User;

import javax.persistence.*;

@Entity
@Table(name = "user_role")
public class UserRole {
    //fields: id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userRoleId;
    //object relations: User
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    //object relations: Role
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    //constructor

    public UserRole() {
    }

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
    }

//getters and setters

    public Long getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Long userRoleId) {
        this.userRoleId = userRoleId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
