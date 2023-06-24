package com.bingo.appbingo.domain.model.session.gateway;


import com.bingo.appbingo.domain.model.auth.Login;
import com.bingo.appbingo.domain.model.session.Session;
import reactor.core.publisher.Mono;

public interface SessionsRepository {
    Mono<Session> entryRegister(Login login);
}
