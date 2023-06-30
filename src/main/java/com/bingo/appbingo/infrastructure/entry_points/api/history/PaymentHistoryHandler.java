package com.bingo.appbingo.infrastructure.entry_points.api.history;

import com.bingo.appbingo.domain.model.history.PaymentHistory;
import com.bingo.appbingo.domain.usecase.history.GetAllFilterHistory;
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
public class PaymentHistoryHandler {
    private final GetAllFilterHistory getAllFilterHistory;

    public Mono<ServerResponse> getAllFilterHistory(ServerRequest serverRequest){
        String page = serverRequest.queryParam("filter").orElse("");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(getAllFilterHistory.apply(page), PaymentHistory.class);
    }
}
