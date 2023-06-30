package com.bingo.appbingo.domain.usecase.history;


import com.bingo.appbingo.domain.model.history.PaymentHistory;
import com.bingo.appbingo.domain.model.history.gateway.PaymentHistoryRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@RequiredArgsConstructor
public class GetAllPaymentHistory implements Function<String , Flux<PaymentHistory>> {
    private final PaymentHistoryRepository paymentHistoryRepository;
    @Override
    public Flux<PaymentHistory> apply(String token) {
        return paymentHistoryRepository.listPayment(token);
    }
}
