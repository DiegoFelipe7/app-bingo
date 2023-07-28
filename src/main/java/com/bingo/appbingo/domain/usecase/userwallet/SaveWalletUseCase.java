package com.bingo.appbingo.domain.usecase.userwallet;

import com.bingo.appbingo.domain.model.userwallet.gateway.UserWalletRepository;
import com.bingo.appbingo.domain.model.utils.Response;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class SaveWalletUseCase implements BiFunction<String, String , Mono<Response>> {
    private final UserWalletRepository userWalletRepositoryAdapter;
    @Override
    public Mono<Response> apply(String token, String wallet) {
        return userWalletRepositoryAdapter.saveWallet(token,wallet);
    }
}
