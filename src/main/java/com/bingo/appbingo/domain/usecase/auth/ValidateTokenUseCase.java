package com.bingo.appbingo.domain.usecase.auth;

import com.bingo.appbingo.domain.model.auth.gateways.AuthRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class ValidateTokenUseCase implements Function<String, Mono<Boolean>> {
    private final AuthRepository authRepository;
    @Override
    public Mono<Boolean> apply(String token) {
        return authRepository.validateToken(token);
    }
}
