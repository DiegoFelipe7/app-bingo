package com.bingo.appbingo.infrastructure.driver_adapter.cardbingo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardBingoDBRepository extends ReactiveMongoRepository<CardBingoEntity , String> , ReactiveCrudRepository<CardBingoEntity,String> {
}
