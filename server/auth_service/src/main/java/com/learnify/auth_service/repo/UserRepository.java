package com.learnify.auth_service.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learnify.auth_service.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

    User findByEmail(String username);

}
