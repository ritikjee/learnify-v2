package com.learnify.auth_service.service;

import java.time.LocalDateTime;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.learnify.auth_service.entity.User;
import com.learnify.auth_service.repo.UserRepository;

@Service
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Cacheable(value = "users", key = "#username", unless = "#result == null")
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmailAndVerified(username, true);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        UserBuilder userBuilder = org.springframework.security.core.userdetails.User.withUsername(username);
        userBuilder.password(user.getPassword());

        return userBuilder.build();
    }

    @Cacheable(value = "users", key = "#user.email", unless = "#result == null")
    public User register(User user) {
        String email = user.getEmail();
        if (email == null || email.isEmpty() || !email.contains("@")) {
            throw new RuntimeException("Please provide a valid email");
        }

        String password = user.getPassword();
        if (password == null || password.isEmpty() || password.length() < 8) {
            throw new RuntimeException("Please provide a valid password");
        }

        User userExists = userRepository.findByEmail(user.getEmail());
        if (userExists != null) {
            throw new RuntimeException("User with email already exists");
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @CacheEvict(value = "users", key = "#email")
    public boolean verifyUser(String email, String token) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found with email: " + email);
        }

        if (!user.getVerificationToken().equals(token)) {
            throw new RuntimeException("Invalid token");
        }

        if (user.getTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired you need to generate a new one");
        }

        user.setVerified(true);
        userRepository.save(user);
        return true;
    }

    @CacheEvict(value = "users", key = "#email")
    public String generateVerificationToken(String email) {
        User user = userRepository.findByEmailAndVerified(email, false);
        if (user == null) {
            throw new RuntimeException("No unverified user not found with email");
        }

        if (user.getTokenExpiry().isAfter(LocalDateTime.now())) {
            throw new RuntimeException("Token already generated");
        }

        String token = user.generateVerificationToken();
        userRepository.save(user);

        return token;
    }

}