package com.bingo.appbingo.domain.usecase.userwallet;

import com.bingo.appbingo.domain.model.userwallet.gateway.UserWalletRepository;
import com.bingo.appbingo.domain.model.utils.Response;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class SaveWalletUseCase implements BiFunction<String, String , Mono<Response>> {
    private final UserWalletRepository userWalletRepository;
    @Override
    public Mono<Response> apply(String token, String wallet) {
        return userWalletRepository.saveWallet(token,wallet);
    }
}
