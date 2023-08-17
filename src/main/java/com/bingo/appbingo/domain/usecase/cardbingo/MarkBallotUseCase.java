package com.bingo.appbingo.domain.usecase.cardbingo;

import com.bingo.appbingo.domain.model.cardbingo.BingoBalls;
import com.bingo.appbingo.domain.model.cardbingo.gateway.CardBingoRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
@RequiredArgsConstructor
public class MarkBallotUseCase {
    private final CardBingoRepository cardBingoRepository;
    public  Mono<BingoBalls>  apply(String lotteryId, Integer round, String ball , String token){
        return cardBingoRepository.markBallot(lotteryId,round,ball , token);
    }

}
