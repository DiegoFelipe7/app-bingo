package com.bingo.appbingo.infrastructure.driver_adapter.cardbingo;

import com.bingo.appbingo.domain.model.cardbingo.BingoCard;
import com.bingo.appbingo.domain.model.cardbingo.CardBingo;
import com.bingo.appbingo.domain.model.cardbingo.gateway.CardBingoRepository;
import com.bingo.appbingo.infrastructure.driver_adapter.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
public class CardBingoAdapterRepository  extends ReactiveAdapterOperations<CardBingo , CardBingoEntity , Integer , CardBingoReactiveRepository> implements CardBingoRepository {
    public CardBingoAdapterRepository(CardBingoReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d-> mapper.mapBuilder(d,CardBingo.CardBingoBuilder.class).build());
    }

    @Override
    public Flux<BingoCard> generateCardBingo() {
        return  null;
    }

    @Override
    public Mono<BingoCard> cardBingo() {
        return null;
    }
}
