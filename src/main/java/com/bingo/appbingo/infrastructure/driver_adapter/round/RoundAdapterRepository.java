package com.bingo.appbingo.infrastructure.driver_adapter.round;

import com.bingo.appbingo.domain.model.round.Round;
import com.bingo.appbingo.domain.model.round.gateway.RoundRepository;
import com.bingo.appbingo.infrastructure.driver_adapter.helper.ReactiveAdapterOperations;
import com.bingo.appbingo.infrastructure.driver_adapter.round.mapper.RoundMapper;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public class RoundAdapterRepository extends ReactiveAdapterOperations<Round, RoundEntity, Integer, RoundReactiveRepository> implements RoundRepository {
    public RoundAdapterRepository(RoundReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.mapBuilder(d, Round.RoundBuilder.class).build());
    }

    @Override
    public Mono<Void> saveRounds(List<Round> rounds, Integer lotteryId) {
        return Flux.fromIterable(rounds)
                .index()
                .concatMap(tuple -> {
                    Integer number = tuple.getT1().intValue() + 1;
                    Round round = tuple.getT2();
                    round.setNumberRound(number);
                    round.setIdLottery(lotteryId);
                    return repository.save(RoundMapper.roundEntity(round));
                })
                .then();
    }

    @Override
    public Flux<Round> getRoundId(Integer id) {
        return repository.findAll()
                .filter(ele -> ele.getId().equals(id))
                .map(RoundMapper::roundEntityARound);
    }

    @Override
    public Flux<Round> getAllRounds(Integer id) {
        return repository.findAll()
                .filter(ele -> ele.getIdLottery().equals(id))
                .map(RoundMapper::roundEntityARound);
    }

}
