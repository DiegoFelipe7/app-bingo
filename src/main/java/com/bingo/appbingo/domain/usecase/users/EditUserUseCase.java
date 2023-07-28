package com.bingo.appbingo.domain.usecase.users;

import com.bingo.appbingo.domain.model.auth.Users;
import com.bingo.appbingo.domain.model.users.gateway.UsersRepository;
import com.bingo.appbingo.domain.model.utils.Response;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class EditUserUseCase implements Function<Users , Mono<Response>> {
    private final UsersRepository usersRepository;

    @Override
    public Mono<Response> apply(Users users) {
        return usersRepository.editUser(users);
    }
}
