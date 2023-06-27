package com.bingo.appbingo.domain.usecase.support;

import com.bingo.appbingo.domain.model.support.Support;
import com.bingo.appbingo.domain.model.support.gateway.SupportRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class GetAllSupportIdUseCase implements Function<Integer , Mono<Support>> {
    private final SupportRepository supportRepository;
    @Override
    public Mono<Support> apply(Integer id) {
        return supportRepository.getAllSupportId(id);
    }
}
