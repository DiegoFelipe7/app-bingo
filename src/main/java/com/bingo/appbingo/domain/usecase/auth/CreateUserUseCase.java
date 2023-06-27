package com.bingo.appbingo.domain.usecase.auth;

import com.bingo.appbingo.domain.model.auth.Users;
import com.bingo.appbingo.domain.model.auth.gateways.AuthRepository;
import com.bingo.appbingo.domain.model.utils.Response;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class CreateUserUseCase implements Function<Users, Mono<Response>> {
    private final AuthRepository authRepository;
    @Override
    public Mono<Response> apply(Users user) {
        return authRepository.accountRegistration(user);
    }
}
