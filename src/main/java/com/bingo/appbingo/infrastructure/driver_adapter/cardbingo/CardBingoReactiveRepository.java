package com.bingo.appbingo.infrastructure.driver_adapter.cardbingo;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardBingoReactiveRepository extends ReactiveCrudRepository<CardBingoEntity , Integer> , ReactiveQueryByExampleExecutor<CardBingoEntity> {
}
