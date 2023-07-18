package com.bingo.appbingo.infrastructure.entry_points.api.cardbingo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
@Configuration
@Slf4j
public class RouterCardBingo {
    private static final String PATH = "api/cardBingo/";

    @Bean
    RouterFunction<ServerResponse> cardBingoRouter(CardBingoHandler cardBingoHandler) {
        return RouterFunctions.route()
                .GET(PATH+"list" , cardBingoHandler::getAllCardBingo)
                .POST(PATH+"save/{lotteryId}" , cardBingoHandler::saveCard)
                .GET(PATH+"validate/{id}" , cardBingoHandler::validateIfPurchase)
                .GET(PATH+"lottery/{lottery}/round/{round}", cardBingoHandler::getCardBingoRound)
                .GET(PATH+"lottery/{id}/users" , cardBingoHandler::getAllCardBingoUser)
                .build();
    }
}
