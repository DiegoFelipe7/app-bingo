package com.bingo.appbingo.infrastructure.driver_adapter.round;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface RoundReactiveRepository extends ReactiveCrudRepository<RoundEntity , Integer> , ReactiveQueryByExampleExecutor<RoundEntity> {
    Mono<RoundEntity> findByIdLotteryAndNumberRound(Integer lottery , Integer round);
}
