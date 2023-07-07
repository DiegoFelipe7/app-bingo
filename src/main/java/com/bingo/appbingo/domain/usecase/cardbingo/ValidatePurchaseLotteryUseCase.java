package com.bingo.appbingo.domain.usecase.cardbingo;

import com.bingo.appbingo.domain.model.cardbingo.gateway.CardBingoRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class ValidatePurchaseLotteryUseCase implements BiFunction<String , Integer , Mono<Boolean>> {
    private final CardBingoRepository  cardBingoRepository;
    @Override
    public Mono<Boolean> apply(String token, Integer id) {
        return cardBingoRepository.validatePurchaseLottery(id,token);
    }
}
