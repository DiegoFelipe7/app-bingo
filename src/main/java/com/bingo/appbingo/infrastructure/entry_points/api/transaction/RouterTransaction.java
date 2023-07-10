package com.bingo.appbingo.infrastructure.entry_points.api.transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@Slf4j
public class RouterTransaction {
    private static final String PATH = "api/transaction/";
    @Bean
    RouterFunction<ServerResponse> transactionRouter(TransactionHandler transactionHandler) {
        return RouterFunctions.route()
                .GET(PATH +"list", transactionHandler::getAllTransaction)
                .GET(PATH +"{id}", transactionHandler::getAllTransactionId)
                .POST(PATH+"save" , transactionHandler::saveTransaction)
                .POST(PATH+"save/userNetwork" , transactionHandler::saveTransactionUserNetwork)
                .PATCH(PATH+"validate/userNetwork/{transaction}" , transactionHandler::validateUserNetwork)
                .PATCH(PATH+"validate/{transaction}" , transactionHandler::validateTransaction)
                .PATCH(PATH+"invalid/{transaction}" , transactionHandler::invalidTransaction)
                .build();
    }
}
