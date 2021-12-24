package com.example.demo.users;

import java.util.List;

import com.example.demo.security.Role;

public class UserEntity {
    private String username;
    private String password;
    private List<String> roles;

    public UserEntity() {

    }

    public UserEntity(String username, String password, List<String> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
        this.roles = List.of(Role.USER);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
