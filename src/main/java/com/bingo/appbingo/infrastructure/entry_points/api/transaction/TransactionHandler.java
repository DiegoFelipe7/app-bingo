package com.bingo.appbingo.infrastructure.entry_points.api.transaction;

import com.bingo.appbingo.domain.model.support.Support;
import com.bingo.appbingo.domain.model.transaction.Transaction;
import com.bingo.appbingo.domain.model.transaction.TransactionDto;
import com.bingo.appbingo.domain.model.utils.Response;
import com.bingo.appbingo.domain.usecase.transaction.GetAllTransactionUseCase;
import com.bingo.appbingo.domain.usecase.transaction.GetIdTransactionUseCase;
import com.bingo.appbingo.domain.usecase.transaction.SaveTransactionUseCase;
import com.bingo.appbingo.domain.usecase.transaction.ValidateTransactionUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class TransactionHandler {
    private final GetAllTransactionUseCase getAllTransactionUseCase;
    private final GetIdTransactionUseCase getIdTransactionUseCase;
    private final SaveTransactionUseCase saveTransactionUseCase;
    private final ValidateTransactionUseCase validateTransactionUseCase;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Mono<ServerResponse> getAllTransaction(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(getAllTransactionUseCase.get(), TransactionDto.class);
    }

    public Mono<ServerResponse> getAllTransactionId(ServerRequest serverRequest) {
        Integer id = Integer.valueOf(serverRequest.pathVariable("id"));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(getIdTransactionUseCase.apply(id), TransactionDto.class);
    }

    public Mono<ServerResponse> saveTransaction(ServerRequest serverRequest) {
        String token = serverRequest.headers().firstHeader("Authorization");
        return serverRequest.bodyToMono(Transaction.class)
                .flatMap(ele -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(saveTransactionUseCase.apply(ele, token), Response.class));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Mono<ServerResponse> validateTransaction(ServerRequest serverRequest) {
        String transaction = serverRequest.pathVariable("transaction");
        return serverRequest.bodyToMono(Transaction.class)
                .flatMap(ele -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(validateTransactionUseCase.apply(transaction), Response.class));
    }
}
