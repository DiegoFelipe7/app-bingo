package com.bingo.appbingo.infrastructure.driver_adapter.history;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentHistoryReactiveRepository extends ReactiveCrudRepository<PaymentHistoryEntity, Integer> , ReactiveQueryByExampleExecutor<PaymentHistoryEntity> {
}
