package com.bingo.appbingo.domain.model.cardbingo.gateway;

import com.bingo.appbingo.domain.model.cardbingo.BingoCard;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CardBingoRepository {

    Flux<BingoCard> generateCardBingo();
    Mono<BingoCard> cardBingo();
}
