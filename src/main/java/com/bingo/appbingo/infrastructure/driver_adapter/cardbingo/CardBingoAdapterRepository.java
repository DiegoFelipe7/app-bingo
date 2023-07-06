package com.bingo.appbingo.infrastructure.driver_adapter.cardbingo;

import com.bingo.appbingo.domain.model.cardbingo.BingoBalls;
import com.bingo.appbingo.domain.model.cardbingo.CardBingo;
import com.bingo.appbingo.domain.model.cardbingo.gateway.CardBingoRepository;
import com.bingo.appbingo.domain.model.utils.Response;
import com.bingo.appbingo.domain.model.utils.TypeStateResponses;
import com.bingo.appbingo.infrastructure.driver_adapter.cardbingo.mapper.CardBingoMapper;
import com.bingo.appbingo.infrastructure.driver_adapter.helper.AdapterOperations;
import com.bingo.appbingo.infrastructure.driver_adapter.helper.Utils;
import com.bingo.appbingo.infrastructure.driver_adapter.security.jwt.JwtProvider;
import com.bingo.appbingo.infrastructure.driver_adapter.users.UsersReactiveRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;


@Repository
public class CardBingoAdapterRepository extends AdapterOperations<CardBingo, CardBingoEntity, String, CardBingoDBRepository> implements CardBingoRepository {
    private final UsersReactiveRepository usersReactiveRepository;
    private final JwtProvider jwtProvider;

    public CardBingoAdapterRepository(CardBingoDBRepository repository, UsersReactiveRepository usersReactiveRepository, JwtProvider jwtProvider, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.mapBuilder(d, CardBingo.CardBingoBuilder.class).build());
        this.usersReactiveRepository = usersReactiveRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public Flux<CardBingo> generateCardBingo() {
        return Flux.range(0, 2)
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
    public Mono<Response> saveCardBingo(List<CardBingo> cardBingo, String token) {
        String username = jwtProvider.extractToken(token);
        return usersReactiveRepository.findByUsername(username)
                .flatMapMany(user -> Flux.fromIterable(cardBingo)
                        .index()
                        .flatMap(card -> {
                            Integer number = card.getT1().intValue() + 1;
                            card.getT2().setUserId(user.getId());
                            card.getT2().setRound(number);
                            return repository.save(CardBingoMapper.cardBingoACardBingoEntity(card.getT2()));
                        })
                        .then(Mono.just(new Response(TypeStateResponses.Success, "Cartones almacenados")))
                ).next();
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
