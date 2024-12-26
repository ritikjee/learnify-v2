package com.learnify.auth_service.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.learnify.auth_service.entity.User;
import com.learnify.auth_service.repo.UserRepository;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        UserBuilder userBuilder = null;

        userBuilder = org.springframework.security.core.userdetails.User.withUsername(username);
        userBuilder.password(new BCryptPasswordEncoder().encode(user.getPassword()));

        return userBuilder.build();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

}
