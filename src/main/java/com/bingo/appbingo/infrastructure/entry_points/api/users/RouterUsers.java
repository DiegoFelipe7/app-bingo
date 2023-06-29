package com.bingo.appbingo.infrastructure.entry_points.api.users;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@Slf4j
public class RouterUsers {
    private static final String PATH = "api/users/";
    @Bean
    RouterFunction<ServerResponse> userRouter(UsersHandler usersHandler) {
        return RouterFunctions.route()
                .GET(PATH+"list" , usersHandler::getAllUsers)
                .GET(PATH +"referrals", usersHandler::references)
                .GET(PATH +"referrals/team", usersHandler::referencesTeam)
                .GET(PATH+"getUser",usersHandler::getUserId)
                .PUT(PATH+"edit",usersHandler::updateUser)
               // .POST(PATH+"seedEmail" , usersHandler::seedEmail)
                .build();
    }
}
