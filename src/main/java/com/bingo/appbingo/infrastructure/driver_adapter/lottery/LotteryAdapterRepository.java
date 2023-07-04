package com.bingo.appbingo.infrastructure.driver_adapter.lottery;

import com.bingo.appbingo.domain.model.lottery.Lottery;
import com.bingo.appbingo.domain.model.lottery.LotteryDto;
import com.bingo.appbingo.domain.model.lottery.gateway.LotteryRepository;
import com.bingo.appbingo.domain.model.utils.Response;
import com.bingo.appbingo.domain.model.utils.TypeStateResponses;
import com.bingo.appbingo.infrastructure.driver_adapter.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class LotteryAdapterRepository extends ReactiveAdapterOperations<Lottery , LotteryEntity ,Integer,LotteryReactiveRepository> implements LotteryRepository {
    public LotteryAdapterRepository(LotteryReactiveRepository repository, ObjectMapper mapper ) {
        super(repository, mapper, d->mapper.mapBuilder(d,Lottery.LotteryBuilder.class).build());
    }

    @Override
    public Mono<Response> saveLottery(LotteryDto lotteryDto) {
        System.out.println(lotteryDto);
        return Mono.just(new Response(TypeStateResponses.Success,"melo"));
    }

    @Override
    public Mono<Response> inactivateLottery() {
        return null;
    }
}
