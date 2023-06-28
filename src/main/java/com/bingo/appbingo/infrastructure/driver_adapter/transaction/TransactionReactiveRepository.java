package com.bingo.appbingo.infrastructure.driver_adapter.transaction;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface TransactionReactiveRepository extends ReactiveCrudRepository<TransactionEntity , Integer> , ReactiveQueryByExampleExecutor<TransactionEntity> {
    Mono<TransactionEntity> findByTransaction(String transaction);
}
