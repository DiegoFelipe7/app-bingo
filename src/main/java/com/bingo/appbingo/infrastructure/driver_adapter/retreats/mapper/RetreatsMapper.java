package com.bingo.appbingo.infrastructure.driver_adapter.retreats.mapper;

import com.bingo.appbingo.domain.model.retreats.Retreats;
import com.bingo.appbingo.infrastructure.driver_adapter.retreats.RetreatsEntity;

public class RetreatsMapper {
    private RetreatsMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static Retreats retreatsEntityARetreats(RetreatsEntity retreatsEntity){
        return Retreats.builder()
                .id(retreatsEntity.getId())
                .wallet(retreatsEntity.getWallet())
                .userWalletId(retreatsEntity.getUserWalletId())
                .price(retreatsEntity.getPrice())
                .commissionPercentage(retreatsEntity.getCommissionPercentage())
                .stateRetreats(retreatsEntity.getStateRetreats())
                .currency(retreatsEntity.getCurrency())
                .createdAt(retreatsEntity.getCreatedAt())
                .updatedAt(retreatsEntity.getUpdatedAt())
                .build();
    }
    public static RetreatsEntity retreatsARetreatsEntity(Retreats retreats){
        return RetreatsEntity.builder()
                .id(retreats.getId())
                .wallet(retreats.getWallet())
                .userWalletId(retreats.getUserWalletId())
                .price(retreats.getPrice())
                .commissionPercentage(retreats.getCommissionPercentage())
                .stateRetreats(retreats.getStateRetreats())
                .currency(retreats.getCurrency())
                .createdAt(retreats.getCreatedAt())
                .updatedAt(retreats.getUpdatedAt())
                .build();
    }
}
