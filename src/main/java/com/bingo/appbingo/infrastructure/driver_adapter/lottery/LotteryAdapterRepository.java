package com.bingo.appbingo.infrastructure.driver_adapter.lottery;

import com.bingo.appbingo.domain.model.lottery.Lottery;
import com.bingo.appbingo.domain.model.lottery.LotteryDto;
import com.bingo.appbingo.domain.model.lottery.gateway.LotteryRepository;
import com.bingo.appbingo.domain.model.round.gateway.RoundRepository;
import com.bingo.appbingo.domain.model.utils.Response;
import com.bingo.appbingo.domain.model.utils.TypeStateResponses;
import com.bingo.appbingo.infrastructure.driver_adapter.helper.ReactiveAdapterOperations;
import com.bingo.appbingo.infrastructure.driver_adapter.lottery.mapper.LotteryMapper;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


@Repository
public class LotteryAdapterRepository extends ReactiveAdapterOperations<Lottery, LotteryEntity, Integer, LotteryReactiveRepository> implements LotteryRepository {
    private final RoundRepository roundRepository;

    public LotteryAdapterRepository(LotteryReactiveRepository repository, RoundRepository roundRepository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.mapBuilder(d, Lottery.LotteryBuilder.class).build());
        this.roundRepository = roundRepository;
    }

    @Override
    public Mono<Response> saveLottery(LotteryDto lotteryDto) {
        return repository.save(LotteryMapper.lotteryDtoALotteryEntity(lotteryDto))
                .flatMap(ele -> roundRepository.saveRounds(lotteryDto.getRounds(), ele.getId()))
                .thenReturn(new Response(TypeStateResponses.Success, "Sorteo creado exitosamente!"));
    }

    @Override
    public Mono<Response> inactivateLottery() {
        return null;
    }

    @Override
    public Mono<Lottery> getLotteryId() {
        return repository.findAll()
                .filter(ele -> ele.getState().equals(Boolean.TRUE))
                .next()
                .map(LotteryMapper::lotteryEntityALottery);
    }
}
