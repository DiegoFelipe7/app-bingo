package com.bingo.appbingo.domain.usecase.retreats;

import com.bingo.appbingo.domain.model.retreats.Retreats;
import com.bingo.appbingo.domain.model.retreats.gateway.RetreatsRepository;
import com.bingo.appbingo.domain.model.utils.Response;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class InvalidUseCase implements Function<Integer, Mono<Response>> {
    private final RetreatsRepository retreatsRepository;

    @Override
    public Mono<Response> apply(Integer id) {
        return retreatsRepository.invalidRetreats(id);
    }

}
