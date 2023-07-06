package com.bingo.appbingo.domain.usecase.cardbingo;


import com.bingo.appbingo.domain.model.cardbingo.CardBingo;
import com.bingo.appbingo.domain.model.cardbingo.gateway.CardBingoRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;


@RequiredArgsConstructor
public class GenerateCardBingoUseCase implements Supplier<Flux<CardBingo>> {
    private final CardBingoRepository cardBingoRepository;

    @Override
    public Flux<CardBingo> get() {
        return cardBingoRepository.generateCardBingo();
    }
}
