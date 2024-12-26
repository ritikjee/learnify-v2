package com.learnify.auth_service.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learnify.auth_service.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User findByEmail(String email);

    User findByEmailAndVerified(String email, boolean verified);

}
