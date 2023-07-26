package com.bingo.appbingo.domain.model.retreats.gateway;

import com.bingo.appbingo.domain.model.retreats.Retreats;
import com.bingo.appbingo.domain.model.utils.Response;
import reactor.core.publisher.Mono;

public interface RetreatsRepository {
    Mono<Response> moneyRequest(Retreats retreats , String token);
}
