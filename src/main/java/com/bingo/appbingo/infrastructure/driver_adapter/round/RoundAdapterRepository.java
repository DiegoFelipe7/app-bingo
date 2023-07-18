package com.bingo.appbingo.infrastructure.driver_adapter.round;

import com.bingo.appbingo.domain.model.balls.gateway.BallRepository;
import com.bingo.appbingo.domain.model.lottery.LotteryDto;
import com.bingo.appbingo.domain.model.lottery.gateway.LotteryRepository;
import com.bingo.appbingo.domain.model.round.Round;
import com.bingo.appbingo.domain.model.round.gateway.RoundRepository;
import com.bingo.appbingo.infrastructure.driver_adapter.exception.CustomException;
import com.bingo.appbingo.infrastructure.driver_adapter.exception.TypeStateResponse;
import com.bingo.appbingo.infrastructure.driver_adapter.helper.ReactiveAdapterOperations;
import com.bingo.appbingo.infrastructure.driver_adapter.lottery.LotteryReactiveRepository;
import com.bingo.appbingo.infrastructure.driver_adapter.round.mapper.RoundMapper;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Repository
public class RoundAdapterRepository extends ReactiveAdapterOperations<Round, RoundEntity, Integer, RoundReactiveRepository> implements RoundRepository {
    private final BallRepository ballRepository;
    private final LotteryReactiveRepository lotteryRepository;
    public RoundAdapterRepository(RoundReactiveRepository repository, LotteryReactiveRepository lotteryRepository,BallRepository ballRepository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.mapBuilder(d, Round.RoundBuilder.class).build());
        this.ballRepository = ballRepository;
        this.lotteryRepository=lotteryRepository;
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
    public Mono<Round> getRoundId(Integer id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST , "Ocurrio un erro" , TypeStateResponse.Error)))
                .map(RoundMapper::roundEntityARound);
    }

    @Override
    public Mono<Round> getLotteryRound(Integer lottery) {
        return lotteryRepository.findById(lottery)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST,"Id de la loterria invalido",TypeStateResponse.Error)))
                .filter(ele->ele.getState().equals(Boolean.TRUE))
                .flatMap(ele->getAllRounds(ele.getId()).next().log());
    }

    @Override
    public Flux<Round> getAllRounds(Integer id) {
        return repository.findAll()
                .filter(ele -> ele.getIdLottery().equals(id) && ele.getCompleted().equals(Boolean.FALSE) && ele.getUserWinner() == null)
                .map(RoundMapper::roundEntityARound)
                .sort(Comparator.comparing(Round::getNumberRound));
    }

    @Override
    public Mono<Round> getNumberRound(Integer id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST,"Error en la ronda" , TypeStateResponse.Error)))
                .map(RoundMapper::roundEntityARound);
    }

    @Override
    public Mono<Void> saveBall(Integer lottery, Integer id) {
        return ballRepository.getAllBall().log()
                .flatMap(ball -> repository.findByIdLotteryAndNumberRound(lottery, id)
                        .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Ronda inválida", TypeStateResponse.Error)))
                        .flatMap(ele -> {
                            List<String> list = new ArrayList<>(ele.getBalls());
                            list.add(ball.getBall());
                            ele.setBalls(list);
                            return repository.save(ele).log();
                        }))
                .then();
    }


}
