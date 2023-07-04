package com.bingo.appbingo.infrastructure.driver_adapter.lottery;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LotteryReactiveRepository extends ReactiveCrudRepository<LotteryEntity , Integer>, ReactiveQueryByExampleExecutor<LotteryEntity> {
}
