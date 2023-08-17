package com.bingo.appbingo.domain.usecase.cardbingo;

import com.bingo.appbingo.domain.model.cardbingo.CardBingo;
import com.bingo.appbingo.domain.model.cardbingo.gateway.CardBingoRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class GetCardBingoUserUseCase implements BiFunction<String , String , Flux<CardBingo>> {
    private final CardBingoRepository cardBingoRepository;
    @Override
    public Flux<CardBingo> apply(String lottery, String token) {
        return cardBingoRepository.getCardBingo(lottery,token);
    }
}
