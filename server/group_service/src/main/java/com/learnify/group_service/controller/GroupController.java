package com.learnify.group_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GroupController {

    @GetMapping
    public String getMethodName() {
        return new String("Hello from group service");
    }

}
