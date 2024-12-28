package com.learnify.api_gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.learnify.api_gateway.interceptor.RequestInterceptor;

@Configuration
public class RequestInterceptorConfig implements WebMvcConfigurer {

    private final RequestInterceptor requestInterceptor;

    public RequestInterceptorConfig(RequestInterceptor requestInterceptor) {
        this.requestInterceptor = requestInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestInterceptor);
    }

}
