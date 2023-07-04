package com.bingo.appbingo.domain.model.history.gateway;

import com.bingo.appbingo.domain.model.history.PaymentHistory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface PaymentHistoryRepository {
    Mono<Void> saveHistory(Integer userId, BigDecimal balance);
    Flux<PaymentHistory> listPayment(String token);

    Flux<PaymentHistory> filterPaymentHistory(String type , String token);

}
