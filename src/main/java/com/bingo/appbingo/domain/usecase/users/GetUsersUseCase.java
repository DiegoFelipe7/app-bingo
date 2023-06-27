package com.bingo.appbingo.domain.usecase.users;

import com.bingo.appbingo.domain.model.auth.Users;
import com.bingo.appbingo.domain.model.users.gateway.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class GetUsersUseCase implements Supplier<Flux<Users>> {
    private final UserRepository userRepository;
    @Override
    public Flux<Users> get() {
        return userRepository.getAllUsers();
    }
}
