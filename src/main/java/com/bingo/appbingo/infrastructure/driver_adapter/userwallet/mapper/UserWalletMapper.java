package com.bingo.appbingo.infrastructure.driver_adapter.userwallet.mapper;

import com.bingo.appbingo.domain.model.userwallet.UserWallet;
import com.bingo.appbingo.infrastructure.driver_adapter.userwallet.UserWalletEntity;

public class UserWalletMapper {
    private UserWalletMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static UserWallet userWalletEntityAUserWallet(UserWalletEntity userWalletEntity){
        return UserWallet.builder()
                .id(userWalletEntity.getId())
                .balance(userWalletEntity.getBalance())
                .userId(userWalletEntity.getUserId())
                .wallet(userWalletEntity.getWallet())
                .state(userWalletEntity.getState())
                .createdAt(userWalletEntity.getCreatedAt())
                .updatedAt(userWalletEntity.getUpdatedAt())
                .build();
    }
    public static UserWalletEntity userWalletAUserWalletEntity(UserWallet userWallet){
        return UserWalletEntity.builder()
                .id(userWallet.getId())
                .balance(userWallet.getBalance())
                .userId(userWallet.getUserId())
                .wallet(userWallet.getWallet())
                .state(userWallet.getState())
                .createdAt(userWallet.getCreatedAt())
                .updatedAt(userWallet.getUpdatedAt())
                .build();
    }
}
