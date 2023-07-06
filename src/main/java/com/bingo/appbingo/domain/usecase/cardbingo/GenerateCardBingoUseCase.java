package com.bingo.appbingo.domain.usecase.cardbingo;

import com.bingo.appbingo.domain.model.cardbingo.CardBingo;
import com.bingo.appbingo.domain.model.cardbingo.CardBingoDto;
import com.bingo.appbingo.domain.model.cardbingo.gateway.CardBingoRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class GenerateCardBingoUseCase implements Supplier<Flux<CardBingoDto>> {
    private final CardBingoRepository cardBingoRepository;

    @Override
    public Flux<CardBingoDto> get() {
        return cardBingoRepository.generateCardBingo();
    }
}
