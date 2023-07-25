package com.bingo.appbingo.domain.model.round.gateway;

import com.bingo.appbingo.domain.model.round.Round;
import com.bingo.appbingo.domain.model.utils.Response;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;

public interface RoundRepository {
    Mono<Void> saveRounds(List<Round> round , Integer lotteryId);
    Mono<Round> getRoundId(Integer id);
    Mono<Round> getLotteryRound(Integer lottery);
    Flux<Round> getAllRounds(Integer id);
    Mono<Round> getNumberRound(Integer id);
    Mono<Response> noRoundWinner(Integer id);
    Mono<Void> saveBall(Integer lottery , Integer round);

    Mono<Boolean> validBalls(Integer id , String ball);

    Mono<Response> winnerRound(Integer id ,Integer userId);
}
