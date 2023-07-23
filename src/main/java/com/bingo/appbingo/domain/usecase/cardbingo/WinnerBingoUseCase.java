package com.bingo.appbingo.domain.usecase.cardbingo;

import com.bingo.appbingo.domain.model.cardbingo.gateway.CardBingoRepository;
import com.bingo.appbingo.domain.model.utils.Response;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class WinnerBingoUseCase {
    private final CardBingoRepository cardBingoRepository;
    public Mono<Response> apply(Integer lotteryId , Integer round , String token){
        return  cardBingoRepository.winnerBingo(lotteryId,round,token);
    }
}
