package com.learnify.auth_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learnify.auth_service.dto.ErrorResponseDTO;
import com.learnify.auth_service.dto.ResponseDTO;
import com.learnify.auth_service.entity.User;
import com.learnify.auth_service.service.UserService;
import com.learnify.auth_service.utils.JwtUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, JwtUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

        try {
            userService.register(user);
            return ResponseEntity.ok(new ResponseDTO<String>(HttpStatus.OK, "User registered successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user, HttpServletResponse response) {
        System.out.println(user.getEmail() + " " + user.getPassword());
        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtils.generateToken(userDetails);

            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(60 * 60 * 10);
            cookie.setPath("/");
            cookie.setSecure(false);

            response.addCookie(cookie);

            return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK, "Login successful"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponseDTO(HttpStatus.UNAUTHORIZED.value(), e.getMessage()));
        }
    }

}
