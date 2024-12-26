package com.learnify.auth_service.service;

import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.learnify.auth_service.entity.User;
import com.learnify.auth_service.repo.UserRepository;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

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

    public User register(User user) {
        String email = user.getEmail();
        if (email == null || email.isEmpty() || !email.contains("@")) {
            throw new RuntimeException("Invalid email");
        }

        String password = user.getPassword();
        if (password == null || password.isEmpty() || password.length() < 8) {
            throw new RuntimeException("Invalid password");
        }

        User userExists = userRepository.findByEmailAndVerified(user.getEmail(), true);
        if (userExists != null) {
            throw new RuntimeException("User already exists");
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

}
