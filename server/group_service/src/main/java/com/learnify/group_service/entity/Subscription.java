package com.learnify.group_service.entity;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private LocalDateTime createdAt = LocalDateTime.now();
    private Integer price;
    private boolean active = false;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "groupId")
    private Group group;
}
