package com.bingo.appbingo.infrastructure.entry_points.api.transaction;

import com.bingo.appbingo.domain.model.transaction.Transaction;
import com.bingo.appbingo.domain.model.transaction.TransactionDto;
import com.bingo.appbingo.domain.model.utils.Response;
import com.bingo.appbingo.domain.usecase.transaction.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class TransactionHandler {
    private final GetAllTransactionUseCase getAllTransactionUseCase;
    private final GetTransactionIdUseCase getTransactionIdUseCase;
    private final SaveTransactionUseCase saveTransactionUseCase;
    private final ValidateTransactionUseCase validateTransactionUseCase;

    private final InvalidTransactionUseCase invalidTransactionUseCase;

   // @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Mono<ServerResponse> getAllTransaction(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(getAllTransactionUseCase.get(), TransactionDto.class);
    }
   // @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Mono<ServerResponse> getAllTransactionId(ServerRequest serverRequest) {
        Integer id = Integer.valueOf(serverRequest.pathVariable("id"));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(getTransactionIdUseCase.apply(id), TransactionDto.class);
    }

    public Mono<ServerResponse> saveTransaction(ServerRequest serverRequest) {
        String token = serverRequest.headers().firstHeader("Authorization");
        return serverRequest.bodyToMono(Transaction.class)
                .flatMap(ele -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(saveTransactionUseCase.apply(ele, token), Response.class));
    }

   // @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Mono<ServerResponse> validateTransaction(ServerRequest serverRequest) {
        String transaction = serverRequest.pathVariable("transaction");
        return serverRequest.bodyToMono(Transaction.class)
                .flatMap(ele -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(validateTransactionUseCase.apply(transaction , ele), Response.class));
    }

//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Mono<ServerResponse> invalidTransaction(ServerRequest serverRequest) {
        String transaction = serverRequest.pathVariable("transaction");
        return serverRequest.bodyToMono(Transaction.class)
                .flatMap(ele -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(invalidTransactionUseCase.apply(transaction), Response.class));
    }
}
