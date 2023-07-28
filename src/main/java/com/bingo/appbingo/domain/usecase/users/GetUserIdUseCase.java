package com.bingo.appbingo.domain.usecase.users;

import com.bingo.appbingo.domain.model.auth.Users;
import com.bingo.appbingo.domain.model.users.gateway.UsersRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class GetUserIdUseCase implements Function<String , Mono<Users>> {
    private final UsersRepository usersRepository;
    @Override
    public Mono<Users> apply(String token) {
        return usersRepository.getUserIdToken(token);
    }
}
