package com.bingo.appbingo.infrastructure.entry_points.api.history;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@Slf4j
public class RouterPaymentHistory {
    private static final String PATH = "api/paymentHistory/";

    @Bean
    RouterFunction<ServerResponse> paymentHistoryRouter(PaymentHistoryHandler paymentHistoryHandler) {
        return RouterFunctions.route()
                .GET(PATH + "filter", paymentHistoryHandler::getAllFilterHistory)
                .build();
    }
}
