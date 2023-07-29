package com.bingo.appbingo.infrastructure.driver_adapter.userwallet;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


@Repository
public interface UserWalletReactiveRepository extends ReactiveCrudRepository<UserWalletEntity, Integer>, ReactiveQueryByExampleExecutor<UserWalletEntity> {
    Mono<UserWalletEntity> findByWalletEqualsIgnoreCase(String wallet);
    Mono<Boolean> existsByWallet(String wallet);
    Mono<UserWalletEntity> findByUserId(Integer id);


}
