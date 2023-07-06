package com.bingo.appbingo.domain.model.passwordreset.gateway;

import com.bingo.appbingo.domain.model.auth.Login;
import com.bingo.appbingo.domain.model.utils.Response;
import reactor.core.publisher.Mono;

public interface PasswordResetRepository {
    Mono<Response> passwordRecovery(Login email);
    Mono<Response> passwordChange(String token, Login login);
}
