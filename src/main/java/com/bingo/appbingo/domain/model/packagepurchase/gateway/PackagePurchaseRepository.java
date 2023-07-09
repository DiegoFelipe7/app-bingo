package com.bingo.appbingo.domain.model.packagepurchase.gateway;


import com.bingo.appbingo.domain.model.packagepurchase.PackagePurchase;
import reactor.core.publisher.Mono;

public interface PackagePurchaseRepository {

    Mono<Void> savePurchase(PackagePurchase packagePurchase);
}
