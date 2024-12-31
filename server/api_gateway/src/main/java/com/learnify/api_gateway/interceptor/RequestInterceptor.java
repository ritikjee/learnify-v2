package com.learnify.api_gateway.interceptor;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnify.api_gateway.config.AuthServiceClient;
import com.learnify.api_gateway.dto.ErrorResponseDTO;
import com.learnify.api_gateway.dto.UserDTO;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RequestInterceptor implements HandlerInterceptor {

    private final AuthServiceClient authServiceClient;
    private final ObjectMapper objectMapper;

    public RequestInterceptor(@Lazy AuthServiceClient authServiceClient, ObjectMapper objectMapper) {
        this.authServiceClient = authServiceClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String requestURI = request.getRequestURI();

        if (requestURI.startsWith("/api/auth/")) {
            return true;
        }

        // Check cookies for authentication
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Not authorized: No cookies found");
            return false;
        }

        Cookie tokenCookie = null;
        for (Cookie cookie : cookies) {
            if ("token".equals(cookie.getName())) {
                tokenCookie = cookie;
                break;
            }
        }

        if (tokenCookie == null) {
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Not authorized: Token cookie missing");
            return false;
        }

        try {
            UserDTO user = authServiceClient.getAuthenticatedUser();
            if (user == null) {
                sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED,
                        "Not authorized: Token expired or invalid");
                return false;
            }

            request.setAttribute("authenticatedUser", user);
        } catch (Exception e) {
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Not authorized: " + e.getMessage());
            return false;
        }

        return true;
    }

    private void sendErrorResponse(HttpServletResponse response, int status, String message) throws Exception {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(status, message);
        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
