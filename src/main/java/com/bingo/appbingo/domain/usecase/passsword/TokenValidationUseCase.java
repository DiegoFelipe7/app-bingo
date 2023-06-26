package com.bingo.appbingo.domain.usecase.passsword;

import com.bingo.appbingo.domain.model.auth.gateways.UserRepository;
import com.bingo.appbingo.domain.model.passwordReset.gateway.PasswordResetRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class TokenValidationUseCase implements Function<String , Mono<Boolean>> {
    private final PasswordResetRepository passwordResetRepository;
    @Override
    public Mono<Boolean> apply(String token) {
        return passwordResetRepository.tokenValidation(token);
    }
}
