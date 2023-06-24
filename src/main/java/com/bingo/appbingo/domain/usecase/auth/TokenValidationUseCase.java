package com.bingo.appbingo.domain.usecase.auth;

import com.bingo.appbingo.domain.model.auth.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class TokenValidationUseCase implements Function<String , Mono<Boolean>> {
    private final UserRepository userRepository;
    @Override
    public Mono<Boolean> apply(String token) {
        return userRepository.tokenValidation(token);
    }
}
