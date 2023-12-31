package com.bingo.appbingo.domain.model.cardbingo.gateway;

import com.bingo.appbingo.domain.model.cardbingo.BingoBalls;

import com.bingo.appbingo.domain.model.cardbingo.CardBingo;
import com.bingo.appbingo.domain.model.utils.Response;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CardBingoRepository {

    Flux<CardBingo> generateCardBingo();
    Mono<List<BingoBalls>> cardBingo();
    Mono<Boolean> validatePurchaseLottery(Integer id , String token);
    Mono<CardBingo> getCardBingoRound(Integer id , Integer round , String token);
    Flux<CardBingo> getCardBingo(Integer id , String token);
    Mono<Response> saveCardBingo(List<CardBingo> cardBingo , String token , Integer lotteryId);

    Mono<Void> markBallot(Integer lotteryId , Integer round , String ball , String token);
}
