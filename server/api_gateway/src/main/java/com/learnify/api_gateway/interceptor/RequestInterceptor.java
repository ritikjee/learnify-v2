package com.learnify.api_gateway.interceptor;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.learnify.api_gateway.config.AuthServiceClient;
import com.learnify.api_gateway.dto.UserDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RequestInterceptor implements HandlerInterceptor {

    private final AuthServiceClient authServiceClient;

    public RequestInterceptor(@Lazy AuthServiceClient authServiceClient) {
        this.authServiceClient = authServiceClient;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        try {
            UserDTO user = authServiceClient.getAuthenticatedUser();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
