package com.bingo.appbingo.infrastructure.driver_adapter.password;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PasswordReactiveResetRepository extends ReactiveCrudRepository<PasswordResetEntity , Integer> , ReactiveQueryByExampleExecutor<PasswordResetEntity> {
    Mono<PasswordResetEntity> findByToken(String token);
}
