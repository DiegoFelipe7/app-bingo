package com.bingo.appbingo.infrastructure.driver_adapter.lottery;

import com.bingo.appbingo.domain.model.lottery.Lottery;
import com.bingo.appbingo.domain.model.lottery.LotteryDto;
import com.bingo.appbingo.domain.model.lottery.gateway.LotteryRepository;
import com.bingo.appbingo.domain.model.retreats.Retreats;
import com.bingo.appbingo.domain.model.round.gateway.RoundRepository;
import com.bingo.appbingo.domain.model.utils.Response;
import com.bingo.appbingo.domain.model.utils.TypeStateResponses;
import com.bingo.appbingo.infrastructure.driver_adapter.exception.CustomException;
import com.bingo.appbingo.infrastructure.driver_adapter.exception.TypeStateResponse;
import com.bingo.appbingo.infrastructure.driver_adapter.helper.ReactiveAdapterOperations;
import com.bingo.appbingo.infrastructure.driver_adapter.helper.Utils;
import com.bingo.appbingo.infrastructure.driver_adapter.lottery.mapper.LotteryMapper;
import com.bingo.appbingo.infrastructure.driver_adapter.round.RoundRepositoryAdapter;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;


@Repository
public class LotteryRepositoryAdapter extends ReactiveAdapterOperations<Lottery, LotteryEntity, Integer, LotteryReactiveRepository> implements LotteryRepository {
    private final RoundRepository roundRepository;

    public LotteryRepositoryAdapter(LotteryReactiveRepository repository, RoundRepositoryAdapter roundRepository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.mapBuilder(d, Lottery.LotteryBuilder.class).build());
        this.roundRepository = roundRepository;
    }

    @Override
    public Mono<Response> saveLottery(LotteryDto lotteryDto) {
        lotteryDto.setKey(Utils.uid());
      return repository.save(LotteryMapper.lotteryDtoALotteryEntity(lotteryDto))
                .flatMap(ele -> roundRepository.saveRounds(lotteryDto.getRounds(), ele.getId()))
                .thenReturn(new Response(TypeStateResponses.Success, "Sorteo creado exitosamente!"));
    }

    @Override
    public Mono<Response> inactivateLottery() {
        return null;
    }




    @Override
    public Flux<Lottery> getLottery() {
        return repository.findAll()
                .map(LotteryMapper::lotteryEntityALottery);
    }

    @Override
    public Mono<Lottery> getLotteryState() {
        return repository.findAll()
                .filter(ele -> ele.getState().equals(Boolean.TRUE))
                .next()
                .map(LotteryMapper::lotteryEntityALottery);

    }

    @Override
    public Mono<LotteryDto> getLotteryId(String id) {
        return repository.findByKey(id)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "El Juego no esta registado", TypeStateResponse.Error)))
                .flatMap(ele -> roundRepository.getAllRounds(ele.getKey()).collectList()
                        .map(res -> LotteryMapper.lotteryDto(ele, res)));
    }

    @Override
    public Flux<LotteryDto> getLotteryStartAdmin() {
        return repository.findAll()
                .filter(ele->ele.getState().equals(Boolean.TRUE))
                .flatMap(ele->getLotteryId(ele.getKey()))
                .sort(Comparator.comparing(LotteryDto::getId));

    }

}
