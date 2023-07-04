package com.bingo.appbingo.infrastructure.driver_adapter.round;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoundReactiveRepository extends ReactiveCrudRepository<RoundEntity , Integer> , ReactiveQueryByExampleExecutor<RoundEntity> {
}
