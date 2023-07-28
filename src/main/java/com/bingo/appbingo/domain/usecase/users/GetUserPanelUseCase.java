package com.bingo.appbingo.domain.usecase.users;

import com.bingo.appbingo.domain.model.users.PanelUsers;
import com.bingo.appbingo.domain.model.users.gateway.UsersRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class GetUserPanelUseCase implements Function<String , Mono<PanelUsers>> {
    private final UsersRepository usersRepository;

    @Override
    public Mono<PanelUsers> apply(String token) {
        return usersRepository.panelUser(token);
    }
}
