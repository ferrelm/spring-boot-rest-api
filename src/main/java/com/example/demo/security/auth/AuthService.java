package com.example.demo.security.auth;

import org.springframework.http.HttpHeaders;

import com.example.demo.security.AuthRequest;
import com.example.demo.security.JwtTokenUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

        @Autowired
        private AuthenticationManager authenticationManager;

        @Autowired
        private JwtTokenUtil jwtTokenUtil;

        public ResponseEntity<Object> login(AuthRequest request) {
                System.out.println("USER: " + request.getUsername() + " // PASS: " + request.getPassword());
                Authentication authenticate = authenticationManager
                                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),
                                                request.getPassword()));

                User user = (User) authenticate.getPrincipal();

                // User user = (User) authenticate.getCredentials();

                /*
                 * HttpHeaders headers = new HttpHeaders();
                 * headers.add("Set-Cookie",
                 * "platform=mobile; Max-Age=604800; Path=/; Secure; HttpOnly");
                 * return ResponseEntity.status(HttpStatus.OK).headers(headers).build();
                 */

                // return user.toString();
                // return jwtTokenUtil.generateAccessToken(user);
                return ResponseEntity.ok()
                                .header(
                                                HttpHeaders.AUTHORIZATION,
                                                jwtTokenUtil.generateAccessToken(user))
                                .body(user.toString());
        }

}
