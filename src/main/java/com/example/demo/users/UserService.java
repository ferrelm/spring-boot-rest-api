package com.example.demo.users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import com.example.demo.MyResourceNotFoundException;
import com.example.demo.security.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    HashMap<String, UserDetails> users = new HashMap<>();

    @PostConstruct
    public void initializeDB() {
        this.create(new UserEntity("admin", "adminPassword", List.of(Role.USER_ADMIN)));
        System.out.println("\ninitializeDB");
        ;
    }

    public UserDetails findByUsername(String username) throws MyResourceNotFoundException {
        System.out.println("findByUsername");
        // System.out.println("USER: " + username + " // PASS: " +
        // users.get(username).getPassword());
        if (users.get(username) != null) {
            System.out.println("USER: " + username + " // PASS: " + users.get(username).getPassword());
            return users.get(username);
        }
        throw new MyResourceNotFoundException("UserService.findByUsername");
    }

    public UserDetails create(UserEntity user) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + Role.USER));
        } else {
            for (String role : user.getRoles()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            }
        }

        UserDetails details = new User(user.getUsername(),
                passwordEncoder.encode(user.getPassword()), authorities);
        // UserDetails details = new User(user.getUsername(), user.getPassword(),
        // authorities);

        return users.put(details.getUsername(), details);
    }

    public HashMap<String, UserDetails> findAllUsers() {
        return users;
    }

    public UserDetails update(String username, UserEntity userEntity) throws Exception {
        if (users.containsKey(username)) {
            UserDetails userDetails = users.get(username);
            return users.put(username,
                    new User(userEntity.getUsername(), userEntity.getPassword(), userDetails.getAuthorities()));
        }

        throw new MyResourceNotFoundException();
    }

    public UserDetails delete(String username) {
        if (users.containsKey(username)) {
            return users.remove(username);
        }
        throw new MyResourceNotFoundException();

    }

}
