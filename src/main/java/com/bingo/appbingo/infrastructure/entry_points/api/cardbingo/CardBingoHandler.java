package com.bingo.appbingo.infrastructure.entry_points.api.cardbingo;

import com.bingo.appbingo.domain.model.cardbingo.BingoCard;
import com.bingo.appbingo.domain.model.history.PaymentHistory;
import com.bingo.appbingo.domain.usecase.cardbingo.GenerateCardBingoUseCase;
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
public class CardBingoHandler {
    private final GenerateCardBingoUseCase generateCardBingoUseCase;

    public Mono<ServerResponse> getAllCardBingo(ServerRequest serverRequest){
        String token = serverRequest.headers().firstHeader("Authorization");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(generateCardBingoUseCase.get(), BingoCard.class);
    }

}
