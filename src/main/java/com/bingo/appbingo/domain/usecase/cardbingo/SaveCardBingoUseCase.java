package com.bingo.appbingo.domain.usecase.cardbingo;

import com.bingo.appbingo.domain.model.cardbingo.CardBingo;
import com.bingo.appbingo.domain.model.cardbingo.gateway.CardBingoRepository;
import com.bingo.appbingo.domain.model.utils.Response;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.BiFunction;

@RequiredArgsConstructor
public class SaveCardBingoUseCase  {
    private final CardBingoRepository cardBingoRepository;
    public Mono<Response> apply(String token, List<CardBingo> cardBingo , Integer lotteryId) {
        return cardBingoRepository.saveCardBingo(cardBingo,token,lotteryId);
    }


}
