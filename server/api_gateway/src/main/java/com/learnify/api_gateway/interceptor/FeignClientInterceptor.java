package com.learnify.api_gateway.interceptor;

import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class FeignClientInterceptor implements RequestInterceptor {

    private final HttpServletRequest request;

    public FeignClientInterceptor(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void apply(RequestTemplate template) {
        String token = null;
        if (request.getCookies() != null) {
            token = java.util.Arrays.stream(request.getCookies())
                    .filter(cookie -> "token".equals(cookie.getName()))
                    .map(cookie -> cookie.getValue())
                    .findFirst()
                    .orElse(null);
        }

        if (token != null) {
            template.header("Cookie", "token=" + token);
        }

    }
}
