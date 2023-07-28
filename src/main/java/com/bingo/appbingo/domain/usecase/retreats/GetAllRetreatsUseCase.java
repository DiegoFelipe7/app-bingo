package com.bingo.appbingo.domain.usecase.retreats;

import com.bingo.appbingo.domain.model.retreats.Retreats;
import com.bingo.appbingo.domain.model.retreats.gateway.RetreatsRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class GetAllRetreatsUseCase implements Supplier<Flux<Retreats>> {
    private final RetreatsRepository retreatsRepository;

    @Override
    public Flux<Retreats> get() {
        return retreatsRepository.getAllRetreats();
    }
}
