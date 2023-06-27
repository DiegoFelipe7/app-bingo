package com.bingo.appbingo.domain.usecase.support;

import com.bingo.appbingo.domain.model.support.Support;
import com.bingo.appbingo.domain.model.support.gateway.SupportRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.function.Function;
@RequiredArgsConstructor
public class GetAllSupportUserUseCase implements Function<String , Flux<Support>> {
    private final SupportRepository supportRepository;
    @Override
    public Flux<Support> apply(String token) {
        return supportRepository.getAllSupportUser(token);
    }
}
