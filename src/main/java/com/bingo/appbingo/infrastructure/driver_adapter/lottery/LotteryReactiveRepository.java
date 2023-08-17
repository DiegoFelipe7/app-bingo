package com.bingo.appbingo.infrastructure.driver_adapter.lottery;

import com.bingo.appbingo.domain.model.cardbingo.CardBingo;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface LotteryReactiveRepository extends ReactiveCrudRepository<LotteryEntity , Integer>, ReactiveQueryByExampleExecutor<LotteryEntity> {
    Mono<LotteryEntity> findByKey(String id);
}
