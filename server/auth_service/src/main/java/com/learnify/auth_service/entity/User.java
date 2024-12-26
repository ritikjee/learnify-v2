package com.learnify.auth_service.entity;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private String id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "verified", nullable = false)
    private boolean verified = false;

    @Column(name = "verification_token", updatable = false)
    private String verificationToken;

    @Column(name = "token_expiry")
    private LocalDateTime tokenExpiry;

    @Column(name = "image")
    private String image;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.generateVerificationToken();
    }

    private void generateVerificationToken() {

        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        this.verificationToken = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);

        this.tokenExpiry = LocalDateTime.now().plusHours(24);
    }
}
