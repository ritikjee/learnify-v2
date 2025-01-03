package com.learnify.auth_service.config;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnify.auth_service.constants.SecurityConstants;
import com.learnify.auth_service.dto.ErrorResponseDTO;
import com.learnify.auth_service.service.AuthService;
import com.learnify.auth_service.utils.JwtUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final AuthService userService;

    @Lazy
    public JwtAuthenticationFilter(JwtUtils jwtUtils, AuthService userService) {
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (Arrays.asList(SecurityConstants.PUBLIC_ENDPOINTS).contains(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        Cookie[] cookies = request.getCookies();
        String token = null;

        if (cookies == null) {
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Token is missing");
            return;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                token = cookie.getValue();
            }
        }

        if (token == null) {
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Token is missing");
            return;
        }

        String username = null;

        try {
            username = jwtUtils.extractUsername(token);
        } catch (Exception e) {
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid token / Token expired");
            return;
        }

        UserDetails userDetails = userService.loadUserByUsername(username);

        if (userDetails == null) {
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid token / Token expired");
            return;
        }

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, null);
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }

    private void sendErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        ErrorResponseDTO responseDTO = new ErrorResponseDTO(status, message);
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(responseDTO));
    }
}
