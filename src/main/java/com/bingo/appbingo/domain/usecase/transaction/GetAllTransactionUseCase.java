package com.bingo.appbingo.domain.usecase.transaction;

import com.bingo.appbingo.domain.model.transaction.TransactionDto;
import com.bingo.appbingo.domain.model.transaction.gateway.TransactionRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class GetAllTransactionUseCase implements Supplier<Flux<TransactionDto>> {
    private final TransactionRepository transactionRepository;
    @Override
    public Flux<TransactionDto> get() {
        return transactionRepository.getAllTransaction();
    }
}
