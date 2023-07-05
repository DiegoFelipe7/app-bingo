package com.bingo.appbingo.infrastructure.driver_adapter.cardbingo;

import com.bingo.appbingo.domain.model.cardbingo.BingoCard;
import com.bingo.appbingo.domain.model.cardbingo.CardBingo;
import com.bingo.appbingo.domain.model.cardbingo.gateway.CardBingoRepository;
import com.bingo.appbingo.domain.model.utils.Response;
import com.bingo.appbingo.domain.model.utils.TypeStateResponses;
import com.bingo.appbingo.infrastructure.driver_adapter.cardbingo.mapper.CardBingoMapper;
import com.bingo.appbingo.infrastructure.driver_adapter.helper.ReactiveAdapterOperations;
import com.bingo.appbingo.infrastructure.driver_adapter.helper.Utils;
import com.bingo.appbingo.infrastructure.driver_adapter.security.jwt.JwtProvider;
import com.bingo.appbingo.infrastructure.driver_adapter.users.UsersReactiveRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


@Repository
public class CardBingoAdapterRepository extends ReactiveAdapterOperations<CardBingo, CardBingoEntity, Integer, CardBingoReactiveRepository> implements CardBingoRepository {
    private final UsersReactiveRepository usersReactiveRepository;
    private final JwtProvider jwtProvider;

    public CardBingoAdapterRepository(CardBingoReactiveRepository repository, UsersReactiveRepository usersReactiveRepository, JwtProvider jwtProvider, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.mapBuilder(d, CardBingo.CardBingoBuilder.class).build());
        this.usersReactiveRepository = usersReactiveRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public Flux<CardBingo> generateCardBingo() {
        return Flux.range(0, 10)
                .flatMap(ele -> cardBingo()
                        .map(data -> new CardBingo(Utils.uid(), data)));
    }

    @Override
    public Mono<List<BingoCard>> cardBingo() {
        return Flux.range(0, 5)
                .flatMap(this::generateBalls)
                .collectList();

    }

    @Override
    public Mono<Response> saveCardBingo(CardBingo cardBingo, String token) {
        String username = jwtProvider.extractToken(token);
        return usersReactiveRepository.findByUsername(username)
                .flatMap(ele -> {
                    cardBingo.setUserId(ele.getId());
                    return repository.save(CardBingoMapper.cardBingoACardBingoEntity(cardBingo))
                            .thenReturn(new Response(TypeStateResponses.Success, "Melo"));
                });

    }

    public Flux<BingoCard> generateBalls(Integer min) {
        return Flux.range(1, 5).map(ele -> {
            int value = (int) (Math.random() * 15) + 1 + 15 * min;
            return new BingoCard(value, false);
        });
    }
}
