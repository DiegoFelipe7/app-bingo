package com.bingo.appbingo.domain.usecase.retreats;

import com.bingo.appbingo.domain.model.retreats.Retreats;
import com.bingo.appbingo.domain.model.retreats.gateway.RetreatsRepository;
import com.bingo.appbingo.domain.model.utils.Response;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class RetreatsUseCase implements BiFunction<Retreats , String , Mono<Response>> {
    private final RetreatsRepository retreatsRepository;
    @Override
    public Mono<Response> apply(Retreats retreats, String wallet) {
        return retreatsRepository.approveMoney(retreats.getId(),wallet,retreats.getPrice());
    }
}
