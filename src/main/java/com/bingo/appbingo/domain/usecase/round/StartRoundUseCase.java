package com.bingo.appbingo.domain.usecase.round;

import com.bingo.appbingo.domain.model.round.gateway.RoundRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import java.util.function.BiFunction;

@RequiredArgsConstructor
public class StartRoundUseCase implements BiFunction<Integer , Integer , Mono<Void>> {
    private final RoundRepository roundRepository;
    @Override
    public Mono<Void> apply(Integer id, Integer lottery) {
        return roundRepository.saveBall(id,lottery);
    }
}
