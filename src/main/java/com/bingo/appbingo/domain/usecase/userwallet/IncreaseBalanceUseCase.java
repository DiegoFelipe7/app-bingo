package com.bingo.appbingo.domain.usecase.userwallet;

import com.bingo.appbingo.domain.model.enums.TypeHistory;
import com.bingo.appbingo.domain.model.userwallet.gateway.UserWalletRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;
import java.util.function.BiFunction;

@RequiredArgsConstructor
public class IncreaseBalanceUseCase  {
    private final UserWalletRepository userWalletRepository;


    public Mono<Void> apply(Integer userId, BigDecimal quantity , TypeHistory typeHistory) {
        return userWalletRepository.increaseBalance(userId,quantity , typeHistory);
    }
}
