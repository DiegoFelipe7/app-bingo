package com.bingo.appbingo.domain.usecase.lottery;

import com.bingo.appbingo.domain.model.lottery.gateway.LotteryRepository;
import com.bingo.appbingo.domain.model.utils.Response;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class InactiveLotteryUseCase implements Function<String , Mono<Response>> {
    private final LotteryRepository lotteryRepository;
    @Override
    public Mono<Response> apply(String key) {
        return lotteryRepository.inactivateLottery(key);
    }
}
