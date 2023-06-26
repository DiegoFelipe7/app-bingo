package com.bingo.appbingo.domain.model.auth.gateways;


import com.bingo.appbingo.domain.model.auth.Login;
import com.bingo.appbingo.domain.model.auth.Token;
import com.bingo.appbingo.domain.model.auth.Users;
import com.bingo.appbingo.domain.model.utils.Response;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<Token> login(Login login);
    Mono<Response>accountRegistration(Users users);
    Mono<Response>referral(Users users);
    Mono<Boolean> validateToken(String token);

    Mono<Response> activateAccount(String token);
}
