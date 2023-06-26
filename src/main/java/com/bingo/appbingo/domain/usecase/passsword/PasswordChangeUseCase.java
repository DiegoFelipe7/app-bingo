package com.bingo.appbingo.domain.usecase.passsword;

import com.bingo.appbingo.domain.model.auth.Login;
import com.bingo.appbingo.domain.model.passwordReset.gateway.PasswordResetRepository;
import com.bingo.appbingo.domain.model.utils.Response;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class PasswordChangeUseCase implements BiFunction<String , Login , Mono<Response>> {
    private final PasswordResetRepository passwordResetRepository;
    @Override
    public Mono<Response> apply(String token, Login login) {
        return passwordResetRepository.passwordChange(token,login);
    }
}
