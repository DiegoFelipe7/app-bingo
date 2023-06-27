package com.bingo.appbingo.domain.usecase.support;

import com.bingo.appbingo.domain.model.support.Support;
import com.bingo.appbingo.domain.model.support.gateway.SupportRepository;
import com.bingo.appbingo.domain.model.utils.Response;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class EditSupportUseCase implements BiFunction<Support, Integer , Mono<Response>> {
    private final SupportRepository supportRepository;
    @Override
    public Mono<Response> apply(Support support, Integer id) {
        return supportRepository.editSupport(support,id);
    }
}
