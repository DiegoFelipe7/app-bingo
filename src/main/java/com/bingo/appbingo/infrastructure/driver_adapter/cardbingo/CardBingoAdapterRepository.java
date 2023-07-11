package com.bingo.appbingo.infrastructure.driver_adapter.cardbingo;

import com.bingo.appbingo.domain.model.cardbingo.BingoBalls;
import com.bingo.appbingo.domain.model.cardbingo.CardBingo;
import com.bingo.appbingo.domain.model.cardbingo.gateway.CardBingoRepository;
import com.bingo.appbingo.domain.model.packagepurchase.PackagePurchase;
import com.bingo.appbingo.domain.model.packagepurchase.gateway.PackagePurchaseRepository;
import com.bingo.appbingo.domain.model.utils.Response;
import com.bingo.appbingo.domain.model.utils.TypeStateResponses;
import com.bingo.appbingo.infrastructure.driver_adapter.cardbingo.mapper.CardBingoMapper;
import com.bingo.appbingo.infrastructure.driver_adapter.helper.AdapterOperations;
import com.bingo.appbingo.infrastructure.driver_adapter.helper.Utils;
import com.bingo.appbingo.infrastructure.driver_adapter.packagepurchase.PackagePurchaseEntity;
import com.bingo.appbingo.infrastructure.driver_adapter.packagepurchase.PackagePurchaseReactiveRepository;
import com.bingo.appbingo.infrastructure.driver_adapter.security.jwt.JwtProvider;
import com.bingo.appbingo.infrastructure.driver_adapter.users.UsersReactiveRepository;
import com.bingo.appbingo.infrastructure.driver_adapter.userwallet.UserWalletRepositoryAdapter;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Repository
public class CardBingoAdapterRepository extends AdapterOperations<CardBingo, CardBingoEntity, String, CardBingoDBRepository> implements CardBingoRepository {
    private final UsersReactiveRepository usersReactiveRepository;
    private final UserWalletRepositoryAdapter userWalletRepositoryAdapter;
    private final JwtProvider jwtProvider;
    private final PackagePurchaseReactiveRepository packagePurchaseRepository;
    private static final BigDecimal price = BigDecimal.valueOf(5);

    public CardBingoAdapterRepository(CardBingoDBRepository repository, UsersReactiveRepository usersReactiveRepository, PackagePurchaseReactiveRepository packagePurchaseRepository, UserWalletRepositoryAdapter userWalletRepositoryAdapter, JwtProvider jwtProvider, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.mapBuilder(d, CardBingo.CardBingoBuilder.class).build());
        this.usersReactiveRepository = usersReactiveRepository;
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
    public Flux<CardBingo> getCardBingo(Integer id, String token) {
        String username = jwtProvider.extractToken(token);
        return usersReactiveRepository.findByUsername(username)
                .flatMapMany(ele -> repository.findAll()
                        .filter(data -> data.getState().equals(Boolean.TRUE) &&
                                data.getLotteryId().equals(id) &&
                                data.getUserId().equals(ele.getId())))
                .map(CardBingoMapper::cardBingoEntityACardBingo);

    }

    public Mono<Response> saveCardBingo(List<CardBingo> cardBingo, String token, Integer lotteryId) {
        String username = jwtProvider.extractToken(token);

        return usersReactiveRepository.findByUsername(username)
                .flatMap(user -> {
                    BigDecimal total = price.multiply(Utils.priceBingo(cardBingo.size()));
                    Mono<Void> planPurchaseMono = planPurchase(cardBingo, user.getId());
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
                            .then(planPurchaseMono)
                            .then(Mono.just(new Response(TypeStateResponses.Success, "Compra de paquete realizada")));
                });
    }

    public Mono<Void> planPurchase(List<CardBingo> list, Integer userId) {
        if (list.size() > 5) {
            return usersReactiveRepository.findById(userId).log()
                    .flatMap(data -> packagePurchaseRepository.save(new PackagePurchaseEntity(data.getId(), data.getUsername(), data.getParentId())))
                    .then();
        }
        return Mono.empty();
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
