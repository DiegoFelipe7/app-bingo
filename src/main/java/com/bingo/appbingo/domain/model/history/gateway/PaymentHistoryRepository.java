package com.bingo.appbingo.domain.model.history.gateway;

import com.bingo.appbingo.domain.model.enums.TypeHistory;
import com.bingo.appbingo.domain.model.history.PaymentHistory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface PaymentHistoryRepository {
    Mono<Void> saveHistory(Integer userId, BigDecimal balance , TypeHistory typeHistory);
    Flux<PaymentHistory> listPayment(String token);

    Flux<PaymentHistory> filterPaymentHistory(TypeHistory type , String token);

}
