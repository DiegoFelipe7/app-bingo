package com.bingo.appbingo.domain.usecase.lottery;

import com.bingo.appbingo.domain.model.lottery.Lottery;
import com.bingo.appbingo.domain.model.lottery.gateway.LotteryRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class GetAllLotteryUseCase implements Supplier<Flux<Lottery>> {
    private final LotteryRepository lotteryRepository;
    @Override
    public Flux<Lottery> get() {
        return lotteryRepository.getLottery();
    }
}
