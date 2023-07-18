package com.bingo.appbingo.domain.usecase.cardbingo;

import com.bingo.appbingo.domain.model.cardbingo.CardBingo;
import com.bingo.appbingo.domain.model.cardbingo.gateway.CardBingoRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class GetCardBingoRoundUseCase {
    private final CardBingoRepository cardBingoRepository;
    public Mono<CardBingo> apply (Integer id , Integer round , String token){
        return cardBingoRepository.getCardBingoRound(id,round,token);
    }
}
