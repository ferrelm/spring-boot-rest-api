package com.example.demo.security;

public class AuthRequest {
    String username;
    String password;

    AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
