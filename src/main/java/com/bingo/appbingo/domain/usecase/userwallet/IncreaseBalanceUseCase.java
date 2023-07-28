package com.bingo.appbingo.domain.usecase.userwallet;

import com.bingo.appbingo.domain.model.enums.TypeHistory;
import com.bingo.appbingo.domain.model.userwallet.gateway.UserWalletRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;

@RequiredArgsConstructor
public class IncreaseBalanceUseCase  {
    private final UserWalletRepository userWalletRepositoryAdapter;


    public Mono<Void> apply(Integer userId, BigDecimal quantity , TypeHistory typeHistory) {
        return userWalletRepositoryAdapter.increaseBalance(userId,quantity , typeHistory);
    }
}
