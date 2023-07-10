package com.bingo.appbingo.domain.usecase.transaction;

import com.bingo.appbingo.domain.model.transaction.Transaction;
import com.bingo.appbingo.domain.model.transaction.gateway.TransactionRepository;
import com.bingo.appbingo.domain.model.utils.Response;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import java.util.function.BiFunction;

@RequiredArgsConstructor
public class SaveTransactionUserNetworkUseCase implements BiFunction<Transaction,String , Mono<Response>> {
    private final TransactionRepository transactionRepository;
    @Override
    public Mono<Response> apply(Transaction transaction, String token) {
        return transactionRepository.saveUserNetwork(transaction,token);
    }
}
