package com.bingo.appbingo.infrastructure.driver_adapter.round;

import com.bingo.appbingo.domain.model.balls.gateway.BallRepository;
import com.bingo.appbingo.domain.model.enums.TypeHistory;
import com.bingo.appbingo.domain.model.lottery.gateway.LotteryRepository;
import com.bingo.appbingo.domain.model.round.Round;
import com.bingo.appbingo.domain.model.round.gateway.RoundRepository;
import com.bingo.appbingo.domain.model.userwallet.gateway.UserWalletRepository;
import com.bingo.appbingo.domain.model.utils.Response;
import com.bingo.appbingo.domain.model.utils.TypeStateResponses;
import com.bingo.appbingo.infrastructure.driver_adapter.balls.BallRepositoryAdapter;
import com.bingo.appbingo.infrastructure.driver_adapter.exception.CustomException;
import com.bingo.appbingo.infrastructure.driver_adapter.exception.TypeStateResponse;
import com.bingo.appbingo.infrastructure.driver_adapter.helper.ReactiveAdapterOperations;
import com.bingo.appbingo.infrastructure.driver_adapter.lottery.LotteryReactiveRepository;
import com.bingo.appbingo.infrastructure.driver_adapter.lottery.LotteryRepositoryAdapter;
import com.bingo.appbingo.infrastructure.driver_adapter.round.mapper.RoundMapper;
import com.bingo.appbingo.infrastructure.driver_adapter.userwallet.UserWalletRepositoryAdapter;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Repository
public class RoundRepositoryAdapter extends ReactiveAdapterOperations<Round, RoundEntity, Integer, RoundReactiveRepository> implements RoundRepository {
    private final BallRepository ballRepository;
    private final UserWalletRepositoryAdapter userWalletRepositoryAdapter;
    private final LotteryReactiveRepository lotteryRepository;

    public RoundRepositoryAdapter(RoundReactiveRepository repository, UserWalletRepositoryAdapter userWalletRepositoryAdapter, LotteryReactiveRepository lotteryRepository, BallRepository ballRepository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.mapBuilder(d, Round.RoundBuilder.class).build());
        this.ballRepository = ballRepository;
        this.lotteryRepository = lotteryRepository;
        this.userWalletRepositoryAdapter = userWalletRepositoryAdapter;
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
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Ocurrio un erro", TypeStateResponse.Error)))
                .map(RoundMapper::roundEntityARound);
    }

    @Override
    public Mono<Round> getLotteryRound(Integer lottery) {
        return lotteryRepository.findById(lottery)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Id de la loterria invalido", TypeStateResponse.Error)))
                .filter(ele -> ele.getState().equals(Boolean.TRUE))
                .flatMap(ele -> getAllRounds(ele.getId()).next().log());
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
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Error en la ronda", TypeStateResponse.Error)))
                .map(RoundMapper::roundEntityARound);
    }

    @Override
    public Mono<Response> noRoundWinner(Integer id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Error en la ronda", TypeStateResponse.Error)))
                .flatMap(ele -> {
                    ele.setUserWinner(1);
                    ele.setCompleted(Boolean.TRUE);
                    ele.setUpdatedAt(LocalDateTime.now());
                    return repository.save(ele)
                            .thenReturn(new Response(TypeStateResponses.Success, "Configuracion realizada"));
                });
    }

    @Override
    public Mono<Void> saveBall(Integer lottery, Integer id) {
        return ballRepository.getAllBall()
                .flatMap(ball -> repository.findByIdLotteryAndNumberRound(lottery, id)
                        .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Ronda inválida", TypeStateResponse.Error)))
                        .flatMap(ele -> {
                            List<String> list = new ArrayList<>(ele.getBalls());
                            list.add(ball.getBall());
                            ele.setBalls(list);
                            return repository.save(ele);
                        }))
                .then();
    }

    @Override
    public Mono<Boolean> validBalls(Integer id, String ball) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "No existe la ronda", TypeStateResponse.Error)))
                .flatMap(ele -> Flux.fromIterable(ele.getBalls()).any(data -> data.equals(ball))).log()
                .defaultIfEmpty(false);
    }

    @Override
    public Mono<Response> winnerRound(Integer id, Integer userId) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Error en la ronda", TypeStateResponse.Error)))
                .flatMap(round -> {
                    if (Boolean.TRUE.equals(round.getCompleted()) && round.getUserWinner() != null) {
                        return Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Ya hay un ganador de bingo en esta ronda", TypeStateResponse.Warning));
                    }
                    round.setCompleted(true);
                    round.setUserWinner(userId);
                    return repository.save(round)
                            .flatMap(savedRound -> userWalletRepositoryAdapter.increaseBalance(userId, savedRound.getAward(), TypeHistory.Earnings)
                                    .thenReturn(new Response(TypeStateResponses.Success, "Felicitaciones")));
                });
    }

}