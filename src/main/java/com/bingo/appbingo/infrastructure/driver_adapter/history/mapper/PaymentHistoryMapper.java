package com.bingo.appbingo.infrastructure.driver_adapter.history.mapper;

import com.bingo.appbingo.domain.model.history.PaymentHistory;
import com.bingo.appbingo.infrastructure.driver_adapter.history.PaymentHistoryEntity;

public class PaymentHistoryMapper {

    private PaymentHistoryMapper() {
        throw new IllegalStateException("Utility class");
    }
    public static PaymentHistory paymentHistoryEntityAPaymentHistory(PaymentHistoryEntity paymentHistory){
        return PaymentHistory.builder()
                .id(paymentHistory.getId())
                .hash(paymentHistory.getHash())
                .balance(paymentHistory.getBalance())
                .currency(paymentHistory.getCurrency())
                .userId(paymentHistory.getUserId())
                .typeHistory(paymentHistory.getTypeHistory())
                .state(paymentHistory.getState())
                .createdAt(paymentHistory.getCreatedAt())
                .build();
    }
    public static PaymentHistoryEntity paymentHistoryAPaymentHistoryEntity(PaymentHistory paymentHistory){
        return PaymentHistoryEntity.builder()
                .id(paymentHistory.getId())
                .hash(paymentHistory.getHash())
                .balance(paymentHistory.getBalance())
                .currency(paymentHistory.getCurrency())
                .userId(paymentHistory.getUserId())
                .typeHistory(paymentHistory.getTypeHistory())
                .state(paymentHistory.getState())
                .createdAt(paymentHistory.getCreatedAt())
                .build();
    }



}
