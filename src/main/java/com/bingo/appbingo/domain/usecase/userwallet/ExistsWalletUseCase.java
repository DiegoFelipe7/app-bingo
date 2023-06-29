package com.bingo.appbingo.domain.usecase.userwallet;

import com.bingo.appbingo.domain.model.userwallet.gateway.UserWalletRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class ExistsWalletUseCase implements Function<String , Mono<Boolean>> {
    private final UserWalletRepository userWalletRepository;
    @Override
    public Mono<Boolean> apply(String token) {
        return userWalletRepository.existWallet(token);
    }
}
