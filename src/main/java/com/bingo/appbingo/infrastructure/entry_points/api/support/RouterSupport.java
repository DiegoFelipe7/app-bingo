package com.bingo.appbingo.infrastructure.entry_points.api.support;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@Slf4j
public class RouterSupport {
    private static final String PATH = "api/support/";
    @Bean
    RouterFunction<ServerResponse> supportRouter(SupportHandler supportHandler) {
        return RouterFunctions.route()
                .GET(PATH +"list", supportHandler::getAllSupport)
                .GET(PATH +"user/{id}", supportHandler::getAllSupportId)
                .GET(PATH +"list/users", supportHandler::getAllSupportUsers)
                .POST(PATH+"save" , supportHandler::registrationSupport)
                .PATCH(PATH+"edit/{id}" , supportHandler::editSupport)
                .build();
    }
}
