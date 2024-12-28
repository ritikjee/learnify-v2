package com.learnify.api_gateway.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.learnify.api_gateway.dto.UserDTO;

@FeignClient(name = "auth-service", url = "http://localhost:8081", path = "/api/user")
public interface AuthServiceClient {

    @GetMapping("/me")
    UserDTO getAuthenticatedUser();

}
