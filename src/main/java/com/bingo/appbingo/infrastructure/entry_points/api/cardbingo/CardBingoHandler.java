package com.bingo.appbingo.infrastructure.entry_points.api.cardbingo;

import com.bingo.appbingo.domain.model.cardbingo.BingoBalls;
import com.bingo.appbingo.domain.model.cardbingo.CardBingo;
import com.bingo.appbingo.domain.model.utils.Response;
import com.bingo.appbingo.domain.usecase.cardbingo.*;
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
    private final GetCardBingoRoundUseCase getCardBingoRoundUseCase;
    private final MarkBallotUseCase markBallotUseCase;

    private  final WinnerBingoUseCase winnerBingoUseCase;
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

    public Mono<ServerResponse> getCardBingoRound(ServerRequest serverRequest) {
        Integer lottery = Integer.valueOf(serverRequest.pathVariable("lottery"));
        Integer roundId = Integer.valueOf(serverRequest.pathVariable("roundId"));
        String token = serverRequest.headers().firstHeader("Authorization");
        Mono<CardBingo> cardBingoMono = getCardBingoRoundUseCase.apply(lottery, roundId, token);
        return cardBingoMono
                .flatMap(cardBingo ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(cardBingo))
                .switchIfEmpty(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> markBallot(ServerRequest serverRequest) {
        Integer lottery = Integer.valueOf(serverRequest.pathVariable("lotteryId"));
        Integer roundId = Integer.valueOf(serverRequest.pathVariable("roundId"));
        String ball = serverRequest.pathVariable("ball");
        String token = serverRequest.headers().firstHeader("Authorization");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(markBallotUseCase.apply(lottery,roundId,ball,token), BingoBalls.class);
    }

    public Mono<ServerResponse> winnerBingo(ServerRequest serverRequest) {
        Integer lottery = Integer.valueOf(serverRequest.pathVariable("lotteryId"));
        Integer roundId = Integer.valueOf(serverRequest.pathVariable("roundId"));
        String token = serverRequest.headers().firstHeader("Authorization");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(winnerBingoUseCase.apply(lottery,roundId,token), Response.class);
    }

}
