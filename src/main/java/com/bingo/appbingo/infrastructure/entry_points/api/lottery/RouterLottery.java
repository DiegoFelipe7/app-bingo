package com.bingo.appbingo.infrastructure.entry_points.api.lottery;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterLottery {
    private final String PATH="api/lottery/";
    @Bean
    RouterFunction<ServerResponse> lotteryRouter(LotteryHandler lotteryHandler){
        return RouterFunctions.route()
                .GET(PATH+"list" , lotteryHandler::getLottery)
                .GET(PATH+"awards" , lotteryHandler::getLottery)
                .POST(PATH+"save",lotteryHandler::saveLottery)
                .build();
    }

}
