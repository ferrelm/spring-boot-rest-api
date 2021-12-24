package com.example.demo.users;

import java.util.HashMap;
import java.util.NoSuchElementException;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;

import com.example.demo.MyResourceNotFoundException;
import com.example.demo.security.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
@RolesAllowed(Role.USER_ADMIN)
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public HashMap<String, UserDetails> findAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/{username}")
    public UserDetails findByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }

    @PostMapping
    public ResponseEntity<UserDetails> create(@RequestBody UserEntity user) {
        System.out.println("createUser");
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(user));
    }

    @PutMapping("/{id}")
    public UserDetails update(@PathVariable String id, @RequestBody UserEntity user) throws Exception {
        return userService.update(id, user);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public UserDetails delete(@PathVariable String id) {
        return userService.delete(id);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ MyResourceNotFoundException.class, NoSuchElementException.class })
    public void handleNotFound(Exception ex, HttpServletRequest req) {
        System.out.println("URL: " + req.getRequestURI());
        System.out.println("Message: " + ex.getMessage());
    }

    // @ResponseStatus(HttpStatus.BAD_REQUEST)
    // @ExceptionHandler(IndexOutOfBoundsException.class)
    // public void myIndexExceptionHandler() {
    // System.out.println("OUT OF BOUNDS - From controller");
    // }
}
