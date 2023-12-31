package com.bingo.appbingo.domain.model.transaction.gateway;

import com.bingo.appbingo.domain.model.transaction.Transaction;
import com.bingo.appbingo.domain.model.transaction.TransactionDto;
import com.bingo.appbingo.domain.model.utils.Response;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionRepository {
    Mono<Response> saveTransaction(Transaction transaction , String token);

    Mono<Response> saveUserNetwork(Transaction transaction , String token);

    Mono<TransactionDto> getTransactionId(Integer id);
    Flux<TransactionDto> getAllTransaction();
    Mono<Response> validateTransactionUserNetwork(String hash );
    Mono<Response> validateTransaction(String hash , Transaction transaction);
    Mono<Response> invalidTransaction(String transaction);
}
