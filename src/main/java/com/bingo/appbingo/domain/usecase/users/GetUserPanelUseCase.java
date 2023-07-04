package com.bingo.appbingo.domain.usecase.users;

import com.bingo.appbingo.domain.model.users.PanelUsers;
import com.bingo.appbingo.domain.model.users.gateway.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class GetUserPanelUseCase implements Function<String , Mono<PanelUsers>> {
    private final UserRepository userRepository;

    @Override
    public Mono<PanelUsers> apply(String token) {
        return userRepository.panelUser(token);
    }
}
