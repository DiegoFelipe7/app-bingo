package com.bingo.appbingo.domain.usecase.history;


import com.bingo.appbingo.domain.model.enums.TypeHistory;
import com.bingo.appbingo.domain.model.history.PaymentHistory;
import com.bingo.appbingo.domain.model.history.gateway.PaymentHistoryRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class GetAllFilterHistoryUseCase implements BiFunction<TypeHistory,String , Flux<PaymentHistory>> {
   private final PaymentHistoryRepository paymentHistoryRepository;
    @Override
    public Flux<PaymentHistory> apply(TypeHistory type, String token) {
        return paymentHistoryRepository.filterPaymentHistory(type,token);
    }
}
