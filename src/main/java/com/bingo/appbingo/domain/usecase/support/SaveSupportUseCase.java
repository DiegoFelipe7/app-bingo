package com.bingo.appbingo.domain.usecase.support;

import com.bingo.appbingo.domain.model.support.Support;
import com.bingo.appbingo.domain.model.support.gateway.SupportRepository;
import com.bingo.appbingo.domain.model.utils.Response;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class SaveSupportUseCase implements BiFunction<Support,String , Mono<Response>> {
    private final SupportRepository supportRepository;
    @Override
    public Mono<Response> apply(Support support, String token) {
        return supportRepository.saveSupport(support,token);
    }
}
