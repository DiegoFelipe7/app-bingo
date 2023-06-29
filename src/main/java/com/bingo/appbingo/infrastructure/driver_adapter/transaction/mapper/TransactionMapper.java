package com.bingo.appbingo.infrastructure.driver_adapter.transaction.mapper;

import com.bingo.appbingo.domain.model.support.Support;
import com.bingo.appbingo.domain.model.transaction.Transaction;
import com.bingo.appbingo.infrastructure.driver_adapter.support.SupportEntity;
import com.bingo.appbingo.infrastructure.driver_adapter.transaction.TransactionEntity;

public class TransactionMapper {
    private TransactionMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static Transaction transactionEntityATransaction(TransactionEntity transaction){
        return Transaction.builder()
                .id(transaction.getId())
                .walletType(transaction.getWalletType())
                .transaction(transaction.getTransaction())
                .price(transaction.getPrice())
                .currency(transaction.getCurrency())
                .urlTransaction(transaction.getUrlTransaction())
                .userId(transaction.getUserId())
                .stateTransaction(transaction.getStateTransaction())
                .state(transaction.getState())
                .createdAt(transaction.getCreatedAt())
                .updatedAt(transaction.getUpdatedAt())
                .build();
    }

    public static TransactionEntity transactionATransactionEntity(Transaction transaction){
        return TransactionEntity.builder()
                .id(transaction.getId())
                .walletType(transaction.getWalletType())
                .transaction(transaction.getTransaction())
                .price(transaction.getPrice())
                .currency(transaction.getCurrency())
                .urlTransaction(transaction.getUrlTransaction())
                .userId(transaction.getUserId())
                .stateTransaction(transaction.getStateTransaction())
                .state(transaction.getState())
                .createdAt(transaction.getCreatedAt())
                .createdAt(transaction.getUpdatedAt())
                .build();
    }
}
