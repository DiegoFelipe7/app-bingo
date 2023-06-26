package com.bingo.appbingo.infrastructure.driver_adapter.auth;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AuthReactiveRepository extends ReactiveCrudRepository<UsersEntity, Integer>, ReactiveQueryByExampleExecutor<UsersEntity> {
    Mono<UsersEntity> findByUsername(String username);
    Mono<UsersEntity> findByEmailIgnoreCase(String email);

    Mono<UsersEntity> findByToken(String token);
}
