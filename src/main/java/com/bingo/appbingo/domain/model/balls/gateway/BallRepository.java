package com.bingo.appbingo.domain.model.balls.gateway;


import com.bingo.appbingo.domain.model.balls.Balls;
import reactor.core.publisher.Mono;

public interface BallRepository {
    Mono<Balls> getAllBall();
}
