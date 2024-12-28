package com.learnify.api_gateway.routes;

import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class Routes {

    @Bean
    public RouterFunction<ServerResponse> authRouterFunction() {
        return GatewayRouterFunctions.route("auth_service")
                .route(RequestPredicates.path("/api/auth/**"), HandlerFunctions.http("http://localhost:8081"))
                .build();

    }

    @Bean
    public RouterFunction<ServerResponse> userRouterFunction() {

        return GatewayRouterFunctions.route("user_service")
                .route(RequestPredicates.path("/api/user/**"), HandlerFunctions.http("http://localhost:8081"))
                .build();
    }
}
