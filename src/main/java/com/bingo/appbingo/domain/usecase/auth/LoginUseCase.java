package com.bingo.appbingo.domain.usecase.auth;


import com.bingo.appbingo.domain.model.auth.Login;
import com.bingo.appbingo.domain.model.auth.Token;
import com.bingo.appbingo.domain.model.auth.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class LoginUseCase implements Function<Login, Mono<Token>> {
    private final UserRepository userRepository;
    @Override
    public Mono<Token> apply(Login login) {
        return userRepository.login(login);
    }
}
