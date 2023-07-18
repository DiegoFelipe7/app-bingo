package com.bingo.appbingo.infrastructure.driver_adapter.cardbingo;

import com.bingo.appbingo.domain.model.cardbingo.BingoBalls;
import com.bingo.appbingo.domain.model.cardbingo.CardBingo;
import com.bingo.appbingo.domain.model.cardbingo.gateway.CardBingoRepository;
import com.bingo.appbingo.domain.model.round.Round;
import com.bingo.appbingo.domain.model.round.gateway.RoundRepository;
import com.bingo.appbingo.domain.model.users.gateway.UserRepository;
import com.bingo.appbingo.domain.model.utils.Response;
import com.bingo.appbingo.domain.model.utils.TypeStateResponses;
import com.bingo.appbingo.infrastructure.driver_adapter.cardbingo.mapper.CardBingoMapper;
import com.bingo.appbingo.infrastructure.driver_adapter.exception.CustomException;
import com.bingo.appbingo.infrastructure.driver_adapter.exception.TypeStateResponse;
import com.bingo.appbingo.infrastructure.driver_adapter.helper.AdapterOperations;
import com.bingo.appbingo.infrastructure.driver_adapter.helper.Utils;
import com.bingo.appbingo.infrastructure.driver_adapter.packagepurchase.PackagePurchaseEntity;
import com.bingo.appbingo.infrastructure.driver_adapter.packagepurchase.PackagePurchaseReactiveRepository;
import com.bingo.appbingo.infrastructure.driver_adapter.security.jwt.JwtProvider;
import com.bingo.appbingo.infrastructure.driver_adapter.users.UsersReactiveRepository;
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


@Repository
public class CardBingoAdapterRepository extends AdapterOperations<CardBingo, CardBingoEntity, String, CardBingoDBRepository> implements CardBingoRepository {
    private final UsersReactiveRepository usersReactiveRepository;
    private final UserRepository userRepository;
    private final UserWalletRepositoryAdapter userWalletRepositoryAdapter;
    private final JwtProvider jwtProvider;
    private final PackagePurchaseReactiveRepository packagePurchaseRepository;
    private final RoundRepository roundRepository;
    private static final BigDecimal price = BigDecimal.valueOf(5);
    private static final Integer SIZE = 25;

    public CardBingoAdapterRepository(CardBingoDBRepository repository, RoundRepository roundRepository, UserRepository userRepository, UsersReactiveRepository usersReactiveRepository, PackagePurchaseReactiveRepository packagePurchaseRepository, UserWalletRepositoryAdapter userWalletRepositoryAdapter, JwtProvider jwtProvider, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.mapBuilder(d, CardBingo.CardBingoBuilder.class).build());
        this.usersReactiveRepository = usersReactiveRepository;
        this.userRepository = userRepository;
        this.roundRepository = roundRepository;
        this.userWalletRepositoryAdapter = userWalletRepositoryAdapter;
        this.jwtProvider = jwtProvider;
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
        String username = jwtProvider.extractToken(token);
        return usersReactiveRepository.findByUsername(username)
                .flatMapMany(ele -> repository.findAll()
                        .filter(data -> data.getState().equals(Boolean.TRUE) &&
                                data.getLotteryId().equals(id) &&
                                data.getUserId().equals(ele.getId())))
                .hasElements();
    }

    @Override
    public Mono<CardBingo> getCardBingoRound(Integer id, Integer round, String token) {
        String username = jwtProvider.extractToken(token);
        return roundRepository.getRoundId(id)
                .flatMap(data -> getCardBingo(id, username)
                        .filter(ele -> ele.getRound().equals(data.getNumberRound())))
                .single();
    }

    @Override
    public Flux<CardBingo> getCardBingo(Integer id, String token) {
        String username = jwtProvider.extractToken(token);
        return usersReactiveRepository.findByUsername(username)
                .flatMapMany(ele -> repository.findAll()
                        .filter(data -> data.getState().equals(Boolean.TRUE) &&
                                data.getLotteryId().equals(id) &&
                                data.getUserId().equals(ele.getId())))
                .map(CardBingoMapper::cardBingoEntityACardBingo)
                .sort(Comparator.comparing(CardBingo::getRound));

    }

    public Mono<Response> saveCardBingo(List<CardBingo> cardBingo, String token, Integer lotteryId) {
        String username = jwtProvider.extractToken(token);
        return usersReactiveRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Error en el token", TypeStateResponse.Error)))
                .flatMap(user -> {
                    BigDecimal total = price.multiply(Utils.priceBingo(cardBingo.size()));
                    Mono<Void> planPurchaseMono = planPurchase(total, user.getId());
                    Mono<Void> updateUser = userRepository.activateUserNetwork(user.getId());
                    return userWalletRepositoryAdapter.decreaseBalance(user.getId(), total)
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

    public Mono<Void> planPurchase(BigDecimal total, Integer userId) {
        if (total.equals(BigDecimal.valueOf(SIZE))) {
            return usersReactiveRepository.findById(userId)
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
