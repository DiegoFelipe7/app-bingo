package com.bingo.appbingo.infrastructure.entry_points.api.retreats;


import com.bingo.appbingo.domain.model.retreats.Retreats;
import com.bingo.appbingo.domain.usecase.retreats.GetAllRetreatsUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class RetreatsHandler {
    private final GetAllRetreatsUseCase getAllRetreatsUseCase;
    public Mono<ServerResponse> getAllRetreats(ServerRequest serverRequest){
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(getAllRetreatsUseCase.get(), Retreats.class);
    }



}
