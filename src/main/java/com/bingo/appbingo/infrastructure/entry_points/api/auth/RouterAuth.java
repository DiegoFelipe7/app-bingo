package com.bingo.appbingo.infrastructure.entry_points.api.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@Slf4j
public class RouterAuth {
    private static final String PATH = "api/auth/";

    @Bean
    RouterFunction<ServerResponse> authRouter(AuthHandler authHandler) {
        return RouterFunctions.route()
                .POST(PATH + "login", authHandler::login)
                .POST(PATH + "create", authHandler::create)
                .POST(PATH+ "recover" , authHandler::passwordRecovery)
                .GET(PATH+"validator/{token}", authHandler::validateToken)
                .GET(PATH+"validate" , authHandler::validateBearerToken)
                .GET(PATH+"activateAccount/{token}" , authHandler::activateAccount)
                .PATCH(PATH+"passwordChange/{token}" ,authHandler::passwordChange)
                .build();
    }
}
