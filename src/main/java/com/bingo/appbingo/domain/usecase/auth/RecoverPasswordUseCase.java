package com.bingo.appbingo.domain.usecase.auth;

import com.bingo.appbingo.domain.model.auth.Login;
import com.bingo.appbingo.domain.model.auth.gateways.UserRepository;
import com.bingo.appbingo.domain.model.utils.Response;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class RecoverPasswordUseCase implements Function<Login , Mono<Response>> {
    private final UserRepository userRepository;
    @Override
    public Mono<Response> apply(Login login) {
        return userRepository.passwordRecovery(login);
    }
}
