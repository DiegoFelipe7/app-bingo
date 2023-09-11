package com.bingo.appbingo.infrastructure.driver_adapter.round;

import com.bingo.appbingo.domain.model.balls.Balls;
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

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "La ronda es invalida", TypeStateResponse.Error)))
                .map(RoundMapper::roundEntityARound);
    }

    @Override
    public Mono<Round> getLotteryRound(String key) {
        return lotteryRepository.findByKey(key)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Id de la loterria invalido", TypeStateResponse.Error)))
                .filter(ele -> ele.getState().equals(Boolean.TRUE))
                .flatMap(ele -> getAllRounds(ele.getKey()).next());
    }

    @Override
    public Flux<Round> getAllRounds(String key) {
        return lotteryRepository.findByKey(key)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Id de la loterria invalido", TypeStateResponse.Error)))
                .flatMapMany(lottery -> repository.findAll()
                        .filter(data -> data.getIdLottery().equals(lottery.getId()) && !data.getCompleted() && data.getUserWinner() == null)
                        .map(RoundMapper::roundEntityARound)
                        .sort(Comparator.comparing(Round::getNumberRound)));

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
                    if (ele.getUserWinner() != null) {
                        return Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Error un usuario ya gano esta ronda de bingo", TypeStateResponse.Error));
                    }
                    ele.setUserWinner(1);
                    ele.setCompleted(Boolean.TRUE);
                    ele.setUpdatedAt(LocalDateTime.now());
                    return repository.save(ele)
                            .thenReturn(new Response(TypeStateResponses.Success, "Ronda terminada"));
                });
    }
    /*
    @Override

    public Mono<Void> saveBall(String lottery, Integer id) {
        var data = ballRepository.getAllBall()
                .flatMap(ball -> lotteryRepository.findByKey(lottery)
                        .   switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Ronda inválida", TypeStateResponse.Error)))
                        .flatMap(lotteryEntity -> repository.findByIdLotteryAndAndId(lotteryEntity.getId(), id)
                                .flatMap(roundEntity -> {
                                    List<String> updatedBalls = new ArrayList<>(roundEntity.getBalls());
                                    updatedBalls.add(ball.getBall());
                                    roundEntity.setBalls(updatedBalls);
                                    return repository.save(roundEntity)
                                })
                        )
                ).map(RoundEntity::getBalls);
    }
*/
    @Override
    public Mono<Void> saveBall(String lottery, Integer id) {
        return lotteryRepository.findByKey(lottery)
                .flatMap(lotteryEntity -> repository.findByIdLotteryAndAndId(lotteryEntity.getId(), id)
                        .flatMap(roundEntity -> {
                            // Obtén las bolas actuales del roundEntity
                            List<String> currentBalls = roundEntity.getBalls();

                            // Obtén todas las bolas disponibles
                            return ballRepository.getAllBall()
                                    .collectList()
                                    .flatMap(allBallsList -> {
                                        if (allBallsList.isEmpty()) {
                                            return Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "No hay bolas disponibles", TypeStateResponse.Error));
                                        }

                                        // Filtra las bolas que no se han utilizado aún
                                        List<Balls> unusedBalls = allBallsList.stream()
                                                .filter(ball -> !currentBalls.contains(ball))
                                                .collect(Collectors.toList());

                                        if (unusedBalls.isEmpty()) {
                                            return Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Todas las bolas han sido utilizadas", TypeStateResponse.Error));
                                        }

                                        // Selecciona aleatoriamente una bola de las no utilizadas
                                        int randomIndex = (int) (Math.random() * unusedBalls.size());
                                        String selectedBall = unusedBalls.get(randomIndex).getBall();

                                        // Agrega la bola al roundEntity
                                        currentBalls.add(selectedBall);
                                        roundEntity.setBalls(currentBalls);

                                        return repository.save(roundEntity).then();
                                    });
                        })
                );
    }





    @Override
    public Mono<Boolean> validBalls(Integer id, String ball) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "No existe la ronda", TypeStateResponse.Error)))
                .flatMap(ele -> Flux.fromIterable(ele.getBalls()).any(data -> data.equals(ball)))
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
                            .flatMap(savedRound -> userWalletRepositoryAdapter.increaseBalanceWinner(userId, savedRound.getAward(), TypeHistory.Earnings)
                                    .thenReturn(new Response(TypeStateResponses.Success, "Felicitaciones")));
                });
    }

}
