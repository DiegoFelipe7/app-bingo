package com.bingo.appbingo.domain.usecase.transaction;

import com.bingo.appbingo.domain.model.transaction.TransactionDto;
import com.bingo.appbingo.domain.model.transaction.gateway.TransactionRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class GetTransactionIdUseCase implements Function<Integer , Mono<TransactionDto>> {
    private final TransactionRepository taTransactionRepository;

    @Override
    public Mono<TransactionDto> apply(Integer id) {
        return taTransactionRepository.getTransactionId(id);
    }
}
