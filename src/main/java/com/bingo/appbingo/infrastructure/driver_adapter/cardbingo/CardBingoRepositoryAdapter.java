package com.bingo.appbingo.infrastructure.driver_adapter.cardbingo;

import com.bingo.appbingo.domain.model.cardbingo.BingoBalls;
import com.bingo.appbingo.domain.model.cardbingo.CardBingo;
import com.bingo.appbingo.domain.model.cardbingo.gateway.CardBingoRepository;
import com.bingo.appbingo.domain.model.enums.TypeHistory;
import com.bingo.appbingo.domain.model.enums.TypeLottery;
import com.bingo.appbingo.domain.model.round.gateway.RoundRepository;
import com.bingo.appbingo.domain.model.users.gateway.UsersRepository;
import com.bingo.appbingo.domain.model.utils.Response;
import com.bingo.appbingo.domain.model.utils.TypeStateResponses;
import com.bingo.appbingo.infrastructure.driver_adapter.cardbingo.mapper.CardBingoMapper;
import com.bingo.appbingo.infrastructure.driver_adapter.exception.CustomException;
import com.bingo.appbingo.infrastructure.driver_adapter.exception.TypeStateResponse;
import com.bingo.appbingo.infrastructure.driver_adapter.helper.AdapterOperations;
import com.bingo.appbingo.infrastructure.driver_adapter.helper.Utils;
import com.bingo.appbingo.infrastructure.driver_adapter.packagepurchase.PackagePurchaseEntity;
import com.bingo.appbingo.infrastructure.driver_adapter.packagepurchase.PackagePurchaseReactiveRepository;
import com.bingo.appbingo.infrastructure.driver_adapter.userwallet.UserWalletRepositoryAdapter;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//TODO: PENDIENTE DE VERIFICAR EL CAMBIO DE USUARIO LA INYECCION REVISAR EL ANTERIO PUSH
@Repository
public class CardBingoRepositoryAdapter extends AdapterOperations<CardBingo, CardBingoEntity, String, CardBingoDBRepository> implements CardBingoRepository {
    private final UsersRepository userRepository;
    private final UserWalletRepositoryAdapter userWalletRepositoryAdapter;
    private final PackagePurchaseReactiveRepository packagePurchaseRepository;
    private final RoundRepository roundRepository;
    private static final BigDecimal price = BigDecimal.valueOf(5);
    private static final Integer SIZE = 25;

    public CardBingoRepositoryAdapter(CardBingoDBRepository repository, RoundRepository roundRepository, UsersRepository userRepository, PackagePurchaseReactiveRepository packagePurchaseRepository, UserWalletRepositoryAdapter userWalletRepositoryAdapter,  ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.mapBuilder(d, CardBingo.CardBingoBuilder.class).build());
        this.userRepository = userRepository;
        this.roundRepository = roundRepository;
        this.userWalletRepositoryAdapter = userWalletRepositoryAdapter;
        this.packagePurchaseRepository = packagePurchaseRepository;
    }

    @Override
    public Flux<CardBingo> generateCardBingo() {
        return Flux.range(0, 10)
                .flatMap(ele -> cardBingo()
                        .map(data -> new CardBingo(Utils.uid(), data)));
    }

    @Override
    public Mono<List<BingoBalls>> cardBingo() {
        return Flux.range(0, 5)
                .flatMap(this::generateBalls)
                .collectList();

    }

    @Override
    public Mono<Boolean> validatePurchaseLottery(Integer id, String token) {
        return userRepository.getUserIdToken(token)
                .flatMapMany(ele -> repository.findAll()
                        .filter(data -> data.getState().equals(Boolean.TRUE) &&
                                data.getLotteryId().equals(id) &&
                                data.getUserId().equals(ele.getId())))
                .hasElements();
    }

    @Override
    public Mono<CardBingo> getCardBingoRound(String lotteryId, Integer round, String token) {
        return roundRepository.getRoundId(round)
                .flatMap(ele -> getCardBingo(lotteryId, token)
                        .filter(data -> data.getRound().equals(ele.getNumberRound()))
                        .next());
    }


    @Override
    public Flux<CardBingo> getCardBingo(String lotteryId, String token) {
        return userRepository.getUserIdToken(token)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Error en el token", TypeStateResponse.Error)))
                .flatMapMany(ele -> repository.findAll()
                        .filter(data -> data.getState().equals(Boolean.TRUE) &&
                                data.getLotteryId().equals(lotteryId) &&
                                data.getUserId().equals(ele.getId())))
                .map(CardBingoMapper::cardBingoEntityACardBingo)
                .sort(Comparator.comparing(CardBingo::getRound));

    }



    @Override
    public Mono<Response> saveCardBingo(List<CardBingo> cardBingo, String token, String lotteryId) {
       return userRepository.getUserIdToken(token)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Error en el token", TypeStateResponse.Error)))
                .flatMap(user -> {
                    BigDecimal total = price.multiply(Utils.priceBingo(cardBingo.size()));
                    Mono<Void> planPurchaseMono = planPurchase(total, user.getId());
                    Mono<Void> updateUser = userRepository.activateUserNetwork(user.getId());
                    return userWalletRepositoryAdapter.decreaseBalance(user.getId(), total , TypeHistory.Shopping)
                            .thenMany(Flux.fromIterable(cardBingo)
                                    .index()
                                    .concatMap(card -> {
                                        Integer number = card.getT1().intValue() + 1;
                                        card.getT2().setUserId(user.getId());
                                        card.getT2().setLotteryId(lotteryId);
                                        card.getT2().setRound(number);
                                        return repository.save(CardBingoMapper.cardBingoACardBingoEntity(card.getT2()));
                                    }))
                            .then(Mono.just(new Response(TypeStateResponses.Success, "Cartones almacenados")))
                            .then(updateUser)
                            .then(planPurchaseMono)
                            .then(Mono.just(new Response(TypeStateResponses.Success, "Compra de paquete realizada")));
                });
    }


    @Override
    public Mono<BingoBalls> markBallot(String lotteryId, Integer round, String ball, String token) {
        return roundRepository.getRoundId(round)
                .flatMap(ele -> roundRepository.validBalls(ele.getId(), ball)
                        .flatMap(data -> {
                            if (Boolean.FALSE.equals(data)) {
                                return Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "La balota es invalida", TypeStateResponse.Error));
                            }
                            return processTypeLAndX(lotteryId, ele.getNumberRound(), ball, token , ele.getTypeGame());
                        }));
    }




    public Mono<BingoBalls> processTypeLAndX(String lotteryId, Integer round, String ball, String token , TypeLottery typeLottery) {
        return getCardBingoRound(lotteryId, round, token)
                .flatMap(card -> {
                    List<Integer> indexes = Utils.processTypeGame(typeLottery);
                    if (indexes.stream().anyMatch(index -> card.getCard().get(index).getNumbers().equals(ball))) {
                        return updateMarkedBallot(card, ball);
                    }
                    return Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Balota invalida", TypeStateResponse.Error));
                });
    }
    public Mono<BingoBalls> updateMarkedBallot(CardBingo card, String ball) {
        return Flux.fromIterable(card.getCard())
                .filter(numBingo -> numBingo.getNumbers().equals(ball))
                .next()
                .flatMap(numberBingo -> {
                    numberBingo.setState(true);
                    return repository.save(CardBingoMapper.cardBingoACardBingoEntity(card)).thenReturn(numberBingo);
                });
    }


    @Override
    public Mono<Response> winnerBingo(String lotteryId, Integer round, String token) {
        return roundRepository.getRoundId(round)
                .flatMap(ele -> getCardBingoRound(lotteryId, ele.getNumberRound(), token)
                        .flatMap(card -> {
                            List<Integer> indexes = Utils.processTypeGame(ele.getTypeGame());
                            if (indexes.stream().allMatch(index -> card.getCard().get(index).getState().equals(Boolean.TRUE))) {
                                return roundRepository.winnerRound(ele.getId(),card.getUserId());
                            }
                            return Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "El bingo no esta completo", TypeStateResponse.Warning));
                        }));
    }

    public Mono<Void> planPurchase(BigDecimal total, Integer userId) {
        if (total.equals(BigDecimal.valueOf(SIZE))) {
            return userRepository.getUserId(userId)
                    .flatMap(data -> packagePurchaseRepository.save(new PackagePurchaseEntity(data.getId(), data.getUsername(), data.getParentId())))
                    .then(userRepository.distributeCommission(userId, total));
        }
        return userRepository.distributeCommission(userId, total);
    }

    public Flux<BingoBalls> generateBalls(Integer min) {
        char[] letters = {'B', 'I', 'N', 'G', 'O'};
        Set<Integer> generatedNumbers = new HashSet<>();
        return Flux.range(1, 5)
                .map(index -> {
                    int value;
                    do {
                        value = (int) (Math.random() * 15) + 1 + 15 * min;
                    } while (generatedNumbers.contains(value));
                    generatedNumbers.add(value);
                    String ballNumber = String.valueOf(letters[min]) + value;
                    return new BingoBalls(ballNumber, false);
                });
    }


}
