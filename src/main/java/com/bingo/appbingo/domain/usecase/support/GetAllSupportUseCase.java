package com.bingo.appbingo.domain.usecase.support;

import com.bingo.appbingo.domain.model.support.SupportDto;
import com.bingo.appbingo.domain.model.support.gateway.SupportRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class GetAllSupportUseCase implements Supplier<Flux<SupportDto>> {
    private final SupportRepository supportRepository;
    @Override
    public Flux<SupportDto> get() {
        return supportRepository.getAllSupport();
    }


}
