package com.bingo.appbingo.domain.usecase.transaction;

import com.bingo.appbingo.domain.model.transaction.gateway.TransactionRepository;
import com.bingo.appbingo.domain.model.utils.Response;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;
@RequiredArgsConstructor
public class InvalidTransactionUseCase implements Function<String , Mono<Response>> {
    private final TransactionRepository transactionRepository;
    @Override
    public Mono<Response> apply(String transaction) {
        return transactionRepository.invalidTransaction(transaction);
    }
}
