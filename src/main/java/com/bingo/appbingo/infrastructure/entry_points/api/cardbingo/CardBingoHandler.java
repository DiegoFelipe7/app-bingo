package com.bingo.appbingo.infrastructure.entry_points.api.cardbingo;

import com.bingo.appbingo.domain.model.cardbingo.CardBingo;
import com.bingo.appbingo.domain.model.utils.Response;
import com.bingo.appbingo.domain.usecase.cardbingo.GenerateCardBingoUseCase;
import com.bingo.appbingo.domain.usecase.cardbingo.GetCardBingoUserUseCase;
import com.bingo.appbingo.domain.usecase.cardbingo.SaveCardBingoUseCase;
import com.bingo.appbingo.domain.usecase.cardbingo.ValidatePurchaseLotteryUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;


@Component
@Slf4j
@RequiredArgsConstructor
public class CardBingoHandler {
    private final GenerateCardBingoUseCase generateCardBingoUseCase;
    private final SaveCardBingoUseCase saveCardBingoUseCase;
    private final ValidatePurchaseLotteryUseCase validatePurchaseLotteryUseCase;
    private final GetCardBingoUserUseCase getCardBingoUserUseCase;
    public Mono<ServerResponse> getAllCardBingo(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(generateCardBingoUseCase.get(), CardBingo.class);
    }

    public Mono<ServerResponse> saveCard(ServerRequest serverRequest) {
        Integer lotteryId = Integer.valueOf(serverRequest.pathVariable("lotteryId"));
        String token = serverRequest.headers().firstHeader("Authorization");
        return serverRequest.bodyToMono(new ParameterizedTypeReference<List<CardBingo>>() {
                })
                .flatMap(ele -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(saveCardBingoUseCase.apply(token, ele,lotteryId), Response.class));
    }

    public Mono<ServerResponse> validateIfPurchase(ServerRequest serverRequest) {
        Integer id = Integer.valueOf(serverRequest.pathVariable("id"));
        String token = serverRequest.headers().firstHeader("Authorization");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(validatePurchaseLotteryUseCase.apply(token,id), Boolean.class);
    }

    public Mono<ServerResponse> getAllCardBingoUser(ServerRequest serverRequest) {
        Integer id = Integer.valueOf(serverRequest.pathVariable("id"));
        String token = serverRequest.headers().firstHeader("Authorization");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(getCardBingoUserUseCase.apply(id,token), CardBingo.class);
    }

}
