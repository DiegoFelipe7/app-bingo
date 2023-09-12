package com.bingo.appbingo.infrastructure.entry_points.api.retreats;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterRetreats {
    private final String PATH="api/retreats/";
    @Bean
     RouterFunction<ServerResponse> RetreatsRouter(RetreatsHandler retreatsHandler){
        return RouterFunctions.route()
                .GET(PATH+"list", retreatsHandler::getAllRetreats)
                .POST(PATH+"request/money",retreatsHandler::saveRetreats)
                .PATCH(PATH+"validate/{transaction}",retreatsHandler::retreals)
                .build();
    }

}
