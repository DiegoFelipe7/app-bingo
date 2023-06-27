package com.bingo.appbingo.domain.usecase.users;

import com.bingo.appbingo.domain.model.users.gateway.UserRepository;
import com.bingo.appbingo.domain.model.utils.Response;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class SaveWalletUseCase implements BiFunction<String, String , Mono<Response>> {
    private final UserRepository userRepository;
    @Override
    public Mono<Response> apply(String token, String wallet) {
        return userRepository.saveWallet(token,wallet);
    }
}
