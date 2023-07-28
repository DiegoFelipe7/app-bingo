package com.bingo.appbingo.infrastructure.driver_adapter.retreats;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RetreatsReactiveRepository extends ReactiveCrudRepository<RetreatsEntity,Integer> , ReactiveQueryByExampleExecutor<RetreatsEntity> {

}
