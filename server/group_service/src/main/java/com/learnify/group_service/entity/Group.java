package com.learnify.group_service.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.learnify.group_service.enums.GroupPrivacy;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String category;

    private String description;
    private String[] thumbnail;
    private String[] gallery;

    private String jsonDescription;
    private String htmlDescription;
    private String icon;

    private Members[] member;

    @Enumerated(EnumType.STRING)
    private GroupPrivacy privacy = GroupPrivacy.PRIVATE;

    private boolean active = false;

    private LocalDateTime createdAt = LocalDateTime.now();
    private String userId;

    @ManyToOne
    @JoinColumn(name = "affiliate_id")
    private Affiliate affiliate;

    private String domain;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subscription> subscription;

}
