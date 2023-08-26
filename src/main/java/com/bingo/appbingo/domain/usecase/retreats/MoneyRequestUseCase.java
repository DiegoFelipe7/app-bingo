package com.bingo.appbingo.domain.usecase.retreats;

import com.bingo.appbingo.domain.model.retreats.Retreats;
import com.bingo.appbingo.domain.model.retreats.gateway.RetreatsRepository;
import com.bingo.appbingo.domain.model.utils.Response;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class MoneyRequestUseCase implements Function<Retreats,Mono<Response>> {
    private final RetreatsRepository retreatsRepository;

    @Override
    public Mono<Response> apply(Retreats retreats) {
        return retreatsRepository.moneyRequest(retreats);
    }
}
