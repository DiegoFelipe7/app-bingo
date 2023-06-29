package com.bingo.appbingo.infrastructure.entry_points.api.userwallet;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@Slf4j
public class RouterUserWallet {
    private final String PATH="api/userWallet/";
    @Bean
    RouterFunction<ServerResponse> userWalletRouter(UserWalletHandler userWalletHandler){
        return RouterFunctions.route()
                .GET(PATH+"exist" , userWalletHandler::existWallet)
                .PATCH(PATH+"registerWallet" , userWalletHandler::saveWallet)
                .build();
    }

}
