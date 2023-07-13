package com.bingo.appbingo.infrastructure.driver_adapter.balls;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BallReactiveRepository extends ReactiveCrudRepository<BallsEntity , Integer> , ReactiveQueryByExampleExecutor<BallsEntity> {
}
