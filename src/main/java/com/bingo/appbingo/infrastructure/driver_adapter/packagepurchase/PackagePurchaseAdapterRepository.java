package com.bingo.appbingo.infrastructure.driver_adapter.packagepurchase;

import com.bingo.appbingo.domain.model.packagepurchase.PackagePurchase;
import com.bingo.appbingo.domain.model.packagepurchase.gateway.PackagePurchaseRepository;
import com.bingo.appbingo.infrastructure.driver_adapter.helper.ReactiveAdapterOperations;
import com.bingo.appbingo.infrastructure.driver_adapter.packagepurchase.mapper.PackagePurchaseMapper;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


@Repository
public class PackagePurchaseAdapterRepository extends ReactiveAdapterOperations<PackagePurchase, PackagePurchaseEntity, Integer, PackagePurchaseReactiveRepository> implements PackagePurchaseRepository {
    public PackagePurchaseAdapterRepository(PackagePurchaseReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.mapBuilder(d, PackagePurchase.PackagePurchaseBuilder.class).build());
    }

    @Override
    public Mono<Void> savePurchase(PackagePurchase packagePurchase) {
        return repository.save(PackagePurchaseMapper.packagePurchaseAPackagePurchaseEntity(packagePurchase))
                .then();
    }
}
