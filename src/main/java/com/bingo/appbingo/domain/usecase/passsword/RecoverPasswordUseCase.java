package com.bingo.appbingo.domain.usecase.passsword;

import com.bingo.appbingo.domain.model.auth.Login;
import com.bingo.appbingo.domain.model.passwordreset.gateway.PasswordResetRepository;
import com.bingo.appbingo.domain.model.utils.Response;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class RecoverPasswordUseCase implements Function<Login , Mono<Response>> {
    private final PasswordResetRepository passwordRecovery;
    @Override
    public Mono<Response> apply(Login login) {
        return passwordRecovery.passwordRecovery(login);
    }
}
