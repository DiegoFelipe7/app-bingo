package com.bingo.appbingo.domain.model.cardbingo.gateway;

import com.bingo.appbingo.domain.model.cardbingo.BingoCard;
import com.bingo.appbingo.domain.model.cardbingo.CardBingo;
import com.bingo.appbingo.domain.model.utils.Response;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CardBingoRepository {

    Flux<CardBingo> generateCardBingo();
    Mono<List<BingoCard>> cardBingo();

    Mono<Response> saveCardBingo(CardBingo cardBingo , String to);
}
