package com.bingo.appbingo.infrastructure.driver_adapter.packagepurchase.mapper;

import com.bingo.appbingo.domain.model.packagepurchase.PackagePurchase;
import com.bingo.appbingo.infrastructure.driver_adapter.packagepurchase.PackagePurchaseEntity;

public class PackagePurchaseMapper {
    private PackagePurchaseMapper() {
        throw new IllegalStateException("Utility class");
    }
    public static PackagePurchase packagePurchaseEntityAPackagePurchase(PackagePurchaseEntity packagePurchase){
        return PackagePurchase.builder()
                .id(packagePurchase.getId())
                .username(packagePurchase.getUsername())
                .userParent(packagePurchase.getUserParent())
                .state(true)
                .build();
    }
    public static PackagePurchaseEntity packagePurchaseAPackagePurchaseEntity(PackagePurchase packagePurchase){
        return PackagePurchaseEntity.builder()
                .id(packagePurchase.getId())
                .username(packagePurchase.getUsername())
                .userParent(packagePurchase.getUserParent())
                .state(true)
                .build();
    }
}
