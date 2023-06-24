package com.bingo.appbingo.domain.usecase.auth;

import com.bingo.appbingo.domain.model.auth.Login;
import com.bingo.appbingo.domain.model.auth.gateways.UserRepository;
import com.bingo.appbingo.domain.model.utils.Response;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class PasswordChangeUseCase implements BiFunction<String , Login , Mono<Response>> {
    private final UserRepository userRepository;
    @Override
    public Mono<Response> apply(String token, Login login) {
        return userRepository.passwordChange(token,login);
    }
}
