package com.bingo.appbingo.domain.usecase.transaction;

import com.bingo.appbingo.domain.model.transaction.Transaction;
import com.bingo.appbingo.domain.model.transaction.gateway.TransactionRepository;
import com.bingo.appbingo.domain.model.utils.Response;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class SaveTransactionUseCase implements BiFunction<Transaction ,String , Mono<Response>> {
    private final TransactionRepository transactionRepository;
    @Override
    public Mono<Response> apply(Transaction transaction, String token) {
        return transactionRepository.saveTransaction(transaction,token);
    }
}
