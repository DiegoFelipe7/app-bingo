package com.bingo.appbingo.domain.usecase.round;

import com.bingo.appbingo.domain.model.balls.Balls;
import com.bingo.appbingo.domain.model.round.gateway.RoundRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.function.BiFunction;

@RequiredArgsConstructor
public class StartRoundUseCase implements BiFunction<String , Integer , Flux<Balls>> {
    private final RoundRepository roundRepository;
    @Override
    public Flux<Balls> apply(String lottery,Integer id ) {
        return roundRepository.saveBall(lottery,id);
    }
}
