package com.bingo.appbingo.domain.usecase.auth;

import com.bingo.appbingo.domain.model.auth.gateways.AuthRepository;
import com.bingo.appbingo.domain.model.utils.Response;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class ActivateAccountUseCase implements Function<String , Mono<Response>> {
    private final AuthRepository authRepository;
    @Override
    public Mono<Response> apply(String token) {
        return authRepository.activateAccount(token);
    }
}
