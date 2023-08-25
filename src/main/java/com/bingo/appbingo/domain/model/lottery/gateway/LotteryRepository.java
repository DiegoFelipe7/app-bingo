package com.bingo.appbingo.domain.model.lottery.gateway;

import com.bingo.appbingo.domain.model.lottery.Lottery;
import com.bingo.appbingo.domain.model.lottery.LotteryDto;
import com.bingo.appbingo.domain.model.utils.Response;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface LotteryRepository {
    Mono<Response> saveLottery(LotteryDto lotteryDto);
    Mono<Response> inactivateLottery(String key);

    Flux<Lottery> getLottery();
    Mono<Lottery> getLotteryState();

    Mono<LotteryDto> getLotteryId(String id);

    Flux<LotteryDto> getLotteryStartAdmin();
    Mono<BigDecimal> priceLottery(String key);
}
