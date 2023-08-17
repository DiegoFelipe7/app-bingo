package com.bingo.appbingo.domain.usecase.lottery;

import com.bingo.appbingo.domain.model.lottery.LotteryDto;
import com.bingo.appbingo.domain.model.lottery.gateway.LotteryRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;
@RequiredArgsConstructor
public class GetLotteryIdUseCase implements Function<String , Mono<LotteryDto>> {
    private final LotteryRepository lotteryRepositoryAdapter;
    @Override
    public Mono<LotteryDto> apply(String id) {
        return lotteryRepositoryAdapter.getLotteryId(id);
    }
}
