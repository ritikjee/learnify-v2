package com.learnify.auth_service.entity;

import java.time.LocalDateTime;
import java.util.UUID;

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

    @Column(name = "verification_token")
    private String verificationToken;

    @Column(name = "token_expiry")
    private LocalDateTime tokenExpiry;

    @Column(name = "image")
    private String image;

    @Column(name = "session_id", nullable = false)
    private String sessionId;

    @Column(name = "forgot_password_token")
    private String forgotPasswordToken;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.verificationToken = this.generateVerificationToken();
        this.sessionId = this.generateSessionId();

    }

    public String generateVerificationToken() {
        return UUID.randomUUID().toString();
    }

    public String generateSessionId() {
        return UUID.randomUUID().toString();
    }
}
