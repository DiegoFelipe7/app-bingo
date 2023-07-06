package com.bingo.appbingo.domain.model.round.gateway;

import com.bingo.appbingo.domain.model.round.Round;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface RoundRepository {
    Mono<Void> saveRounds(List<Round> round , Integer lotteryId);
    Flux<Round> getRoundId(Integer id);
}
