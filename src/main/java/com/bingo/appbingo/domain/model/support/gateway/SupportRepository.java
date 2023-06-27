package com.bingo.appbingo.domain.model.support.gateway;

import com.bingo.appbingo.domain.model.support.Support;
import com.bingo.appbingo.domain.model.support.SupportDto;
import com.bingo.appbingo.domain.model.utils.Response;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SupportRepository {
    Flux<SupportDto> getAllSupport();
    Flux<Support> getAllSupportUser(String token);
    Mono<Support> getAllSupportId(Integer id);
    Mono<Response> saveSupport(Support support , String token);
    Mono<Response> editSupport(Support support , Integer id);


}
