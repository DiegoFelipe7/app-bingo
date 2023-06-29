package com.bingo.appbingo.domain.usecase.userwallet;

import com.bingo.appbingo.domain.model.userwallet.gateway.UserWalletRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;
import java.util.function.BiFunction;

@RequiredArgsConstructor
public class IncreaseBalanceUseCase implements BiFunction<Integer , BigDecimal, Mono<Void>> {
    private final UserWalletRepository userWalletRepository;

    @Override
    public Mono<Void> apply(Integer userId, BigDecimal quantity) {
        return userWalletRepository.increaseBalance(userId,quantity);
    }
}
