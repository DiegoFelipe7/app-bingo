package com.bingo.appbingo.domain.usecase.lottery;

import com.bingo.appbingo.domain.model.lottery.Lottery;
import com.bingo.appbingo.domain.model.lottery.gateway.LotteryRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class GetLotteryAvailableUseCase implements Supplier<Mono<Lottery>> {
    private final LotteryRepository lotteryRepositoryAdapter;
    @Override
    public Mono<Lottery> get() {
        return lotteryRepositoryAdapter.getLotteryState();
    }
}
