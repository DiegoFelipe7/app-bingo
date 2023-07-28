package com.bingo.appbingo.domain.usecase.lottery;

import com.bingo.appbingo.domain.model.lottery.LotteryDto;
import com.bingo.appbingo.domain.model.lottery.gateway.LotteryRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class GetLotteryStarAdminUseCase implements Supplier<Mono<LotteryDto>> {
    private final LotteryRepository lotteryRepositoryAdapter;
    @Override
    public Mono<LotteryDto> get() {
        return lotteryRepositoryAdapter.getLotteryStartAdmin();
    }
}
