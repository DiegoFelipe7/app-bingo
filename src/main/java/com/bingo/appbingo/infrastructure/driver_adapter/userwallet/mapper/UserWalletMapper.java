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
                .red(userWalletEntity.getRed())
                .balance(userWalletEntity.getBalance())
                .userId(userWalletEntity.getUserId())
                .wallet(userWalletEntity.getWallet())
                .currency(userWalletEntity.getCurrency())
                .bingoWinnings(userWalletEntity.getBingoWinnings())
                .state(userWalletEntity.getState())
                .createdAt(userWalletEntity.getCreatedAt())
                .updatedAt(userWalletEntity.getUpdatedAt())
                .build();
    }
    public static UserWalletEntity userWalletAUserWalletEntity(UserWallet userWallet){
        return UserWalletEntity.builder()
                .id(userWallet.getId())
                .red(userWallet.getRed())
                .balance(userWallet.getBalance())
                .userId(userWallet.getUserId())
                .wallet(userWallet.getWallet())
                .currency(userWallet.getCurrency())
                .bingoWinnings(userWallet.getBingoWinnings())
                .state(userWallet.getState())
                .createdAt(userWallet.getCreatedAt())
                .updatedAt(userWallet.getUpdatedAt())
                .build();
    }
}
