package com.bingo.appbingo.domain.usecase.users;

import com.bingo.appbingo.domain.model.users.gateway.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.function.BiFunction;

@RequiredArgsConstructor
public class ParaBorarrUseCase implements BiFunction<BigDecimal , Integer , Mono<Void>> {
    private final UserRepository userRepository;
    @Override
    public Mono<Void> apply(BigDecimal bigDecimal, Integer integer) {
        return userRepository.distributeCommission(integer,bigDecimal);
    }
}
