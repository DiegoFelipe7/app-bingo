package com.bingo.appbingo.domain.usecase.users;

import com.bingo.appbingo.domain.model.users.gateway.UsersRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class UserWinnerUseCase implements Function<Integer , Mono<String>> {
    private final UsersRepository usersRepository;
    @Override
    public Mono<String> apply(Integer id) {
        return usersRepository.userWinner(id);
    }
}
