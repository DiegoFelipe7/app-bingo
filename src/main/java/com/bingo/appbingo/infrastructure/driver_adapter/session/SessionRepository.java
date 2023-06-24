package com.bingo.appbingo.infrastructure.driver_adapter.session;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends ReactiveCrudRepository<SessionEntity , Integer>, ReactiveQueryByExampleExecutor<SessionEntity> {
}
