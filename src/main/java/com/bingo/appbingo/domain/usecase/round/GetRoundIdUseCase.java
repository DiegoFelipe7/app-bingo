package com.bingo.appbingo.domain.usecase.round;

import com.bingo.appbingo.domain.model.round.Round;
import com.bingo.appbingo.domain.model.round.gateway.RoundRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class GetRoundIdUseCase implements Function<Integer , Mono<Round>> {
    private final RoundRepository roundRepository;
    @Override
    public Mono<Round> apply(Integer id) {
        return roundRepository.getRoundId(id);
    }
}
