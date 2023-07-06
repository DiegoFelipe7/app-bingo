package com.bingo.appbingo.infrastructure.driver_adapter.cardbingo;

import com.bingo.appbingo.domain.model.cardbingo.BingoBalls;
import com.bingo.appbingo.domain.model.cardbingo.CardBingo;
import com.bingo.appbingo.domain.model.cardbingo.CardBingoDto;
import com.bingo.appbingo.domain.model.cardbingo.gateway.CardBingoRepository;
import com.bingo.appbingo.domain.model.utils.Response;
import com.bingo.appbingo.domain.model.utils.TypeStateResponses;
import com.bingo.appbingo.infrastructure.driver_adapter.bingoBalls.BingoBallReactiveRepository;
import com.bingo.appbingo.infrastructure.driver_adapter.bingoBalls.mapper.BingoBallsMapper;
import com.bingo.appbingo.infrastructure.driver_adapter.cardbingo.mapper.CardBingoMapper;
import com.bingo.appbingo.infrastructure.driver_adapter.helper.ReactiveAdapterOperations;
import com.bingo.appbingo.infrastructure.driver_adapter.helper.Utils;
import com.bingo.appbingo.infrastructure.driver_adapter.security.jwt.JwtProvider;
import com.bingo.appbingo.infrastructure.driver_adapter.users.UsersReactiveRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;


@Repository
public class CardBingoAdapterRepository extends ReactiveAdapterOperations<CardBingo, CardBingoEntity, Integer, CardBingoReactiveRepository> implements CardBingoRepository {
    private final UsersReactiveRepository usersReactiveRepository;
    private final BingoBallReactiveRepository bingoBallReactiveRepository;
    private final JwtProvider jwtProvider;

    public CardBingoAdapterRepository(CardBingoReactiveRepository repository, UsersReactiveRepository usersReactiveRepository, BingoBallReactiveRepository bingoBallReactiveRepository, JwtProvider jwtProvider, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.mapBuilder(d, CardBingo.CardBingoBuilder.class).build());
        this.usersReactiveRepository = usersReactiveRepository;
        this.bingoBallReactiveRepository = bingoBallReactiveRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public Flux<CardBingoDto> generateCardBingo() {
        return Flux.range(0, 2)
                .flatMap(ele -> cardBingo()
                        .map(data -> new CardBingoDto(Utils.uid(), data)));
    }

    @Override
    public Mono<List<BingoBalls>> cardBingo() {
        return Flux.range(0, 5)
                .flatMap(this::generateBalls)
                .collectList();

    }

    @Override
    public Mono<Response> saveCardBingo(List<CardBingoDto> cardBingo, String token) {
        String username = jwtProvider.extractToken(token);
        return usersReactiveRepository.findByUsername(username)
                .delayElement(Duration.ofSeconds(5))
                .flatMapMany(user -> Flux.fromIterable(cardBingo)
                        .concatMap(card -> {
                            card.setUserId(user.getId());
                            return repository.save(CardBingoMapper.cardBingoDtoACardBingoEntity(card))
                                    .flatMapMany(savedCard -> Flux.fromIterable(card.getCard())
                                            .concatMap(ball -> {
                                                ball.setCardBingoId(savedCard.getId());
                                                return bingoBallReactiveRepository.save(BingoBallsMapper.bingoBallsABingoBallsEntity(ball));
                                            }));
                        }))
                .then(Mono.just(new Response(TypeStateResponses.Success, "Tarjetas de Bingo guardadas correctamente")));
    }





    public Flux<BingoBalls> generateBalls(Integer min) {
        char[] letters = {'B', 'I', 'N', 'G', 'O'};
        return Flux.range(1, 5)
                .map(index -> {
                    int value = (int) (Math.random() * 15) + 1 + 15 * min;
                    String ballNumber = String.valueOf(letters[min]) + value;
                    return new BingoBalls(ballNumber, false);
                });
    }

}
