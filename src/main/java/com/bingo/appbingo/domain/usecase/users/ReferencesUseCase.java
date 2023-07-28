package com.bingo.appbingo.domain.usecase.users;

import com.bingo.appbingo.domain.model.users.References;
import com.bingo.appbingo.domain.model.users.gateway.UsersRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@RequiredArgsConstructor
public class ReferencesUseCase implements Function<String , Flux<References>> {
    private final UsersRepository usersRepository;

    @Override
    public Flux<References> apply(String token) {
        return usersRepository.getAllReferences(token);
    }
}
