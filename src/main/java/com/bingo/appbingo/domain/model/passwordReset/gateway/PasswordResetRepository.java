package com.bingo.appbingo.domain.model.passwordReset.gateway;

import com.bingo.appbingo.domain.model.auth.Login;
import com.bingo.appbingo.domain.model.utils.Response;
import reactor.core.publisher.Mono;

public interface PasswordResetRepository {
    Mono<Response> passwordRecovery(Login email);
    Mono<Boolean> tokenValidation(String token);
    Mono<Response> passwordChange(String token, Login login);
}
