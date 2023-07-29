package com.bingo.appbingo.domain.model.retreats.gateway;

import com.bingo.appbingo.domain.model.retreats.Retreats;
import com.bingo.appbingo.domain.model.utils.Response;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface RetreatsRepository {
    Mono<Response> moneyRequest(Retreats retreats , String token);
    Mono<Response> approveMoney(String wallet , BigDecimal money);

    Flux<Retreats> getAllRetreats();
}
