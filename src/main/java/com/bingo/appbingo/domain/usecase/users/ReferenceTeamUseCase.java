package com.bingo.appbingo.domain.usecase.users;

import com.bingo.appbingo.domain.model.users.References;
import com.bingo.appbingo.domain.model.users.gateway.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@RequiredArgsConstructor
public class ReferenceTeamUseCase implements Function<String , Flux<References>> {
    private final UserRepository userRepository;

    @Override
    public Flux<References> apply(String token) {
        return userRepository.getAllReferencesTeam(token);
    }
}
