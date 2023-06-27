package com.bingo.appbingo.infrastructure.driver_adapter.support;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupportReactiveRepository extends ReactiveCrudRepository<SupportEntity, Integer>, ReactiveQueryByExampleExecutor<SupportEntity> {
}
