package com.bingo.appbingo.infrastructure.driver_adapter.bingoBalls;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BingoBallReactiveRepository extends ReactiveCrudRepository<BingoBallsEntity ,Integer> , ReactiveQueryByExampleExecutor<BingoBallsEntity> {
}
