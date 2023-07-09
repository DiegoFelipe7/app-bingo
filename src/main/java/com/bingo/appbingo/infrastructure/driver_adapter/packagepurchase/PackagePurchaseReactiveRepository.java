package com.bingo.appbingo.infrastructure.driver_adapter.packagepurchase;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackagePurchaseReactiveRepository  extends ReactiveCrudRepository<PackagePurchaseEntity , Integer> , ReactiveQueryByExampleExecutor<PackagePurchaseEntity> {
}
