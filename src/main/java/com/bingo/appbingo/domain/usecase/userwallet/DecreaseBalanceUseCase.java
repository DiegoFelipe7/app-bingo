package com.bingo.appbingo.domain.usecase.userwallet;

import com.bingo.appbingo.domain.model.userwallet.gateway.UserWalletRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.function.BiFunction;

@RequiredArgsConstructor
public class DecreaseBalanceUseCase  implements BiFunction<Integer,BigDecimal , Mono<Void> > {
    private final UserWalletRepository userWalletRepository;

    @Override
    public Mono<Void> apply(Integer integer, BigDecimal bigDecimal) {
        return null;
    }
}
