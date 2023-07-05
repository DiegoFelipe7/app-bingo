package com.bingo.appbingo.domain.usecase.cardbingo;

import com.bingo.appbingo.domain.model.cardbingo.BingoCard;
import com.bingo.appbingo.domain.model.cardbingo.gateway.CardBingoRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class GenerateCardBingoUseCase implements Supplier<Flux<BingoCard>> {
    private final CardBingoRepository cardBingoRepository;

    @Override
    public Flux<BingoCard> get() {
        return cardBingoRepository.generateCardBingo();
    }
}
