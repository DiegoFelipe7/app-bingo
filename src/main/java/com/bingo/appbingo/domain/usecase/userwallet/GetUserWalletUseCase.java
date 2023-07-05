package com.bingo.appbingo.domain.usecase.userwallet;

import com.bingo.appbingo.domain.model.userwallet.UserWallet;
import com.bingo.appbingo.domain.model.userwallet.gateway.UserWalletRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class GetUserWalletUseCase implements Function<String , Mono<UserWallet>> {
    private final UserWalletRepository userWalletRepository;
    @Override
    public Mono<UserWallet> apply(String token) {
        return userWalletRepository.getWalletUser(token);
    }
}
