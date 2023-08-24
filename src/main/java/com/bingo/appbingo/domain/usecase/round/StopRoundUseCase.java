package com.bingo.appbingo.domain.usecase.round;

import com.bingo.appbingo.domain.model.round.gateway.RoundRepository;
import com.bingo.appbingo.domain.model.utils.Response;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class StopRoundUseCase implements Function<Integer , Mono<Response>> {
    private final RoundRepository roundRepository;


    @Override
    public Mono<Response> apply(Integer id) {
        return roundRepository.noRoundWinner(id);
    }
}
