package com.learnify.group_service.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learnify.group_service.entity.Group;

public interface GroupRepository extends JpaRepository<Group, String> {

}
