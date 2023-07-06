package com.bingo.appbingo.infrastructure.entry_points.api.lottery;

import com.bingo.appbingo.domain.model.lottery.Lottery;
import com.bingo.appbingo.domain.model.lottery.LotteryDto;
import com.bingo.appbingo.domain.model.utils.Response;
import com.bingo.appbingo.domain.usecase.lottery.GetAllLotteryUseCase;
import com.bingo.appbingo.domain.usecase.lottery.GetLotteryAvailableUseCase;
import com.bingo.appbingo.domain.usecase.lottery.GetLotteryIdUseCase;
import com.bingo.appbingo.domain.usecase.lottery.SaveLotteryUseCase;
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
public class LotteryHandler {
    private final SaveLotteryUseCase saveLotteryUseCase;
    private final GetAllLotteryUseCase getAllLotteryUseCase;
    private final GetLotteryIdUseCase getLotteryIdUseCase;
    private final GetLotteryAvailableUseCase getLotteryAvailableUseCase;

    public Mono<ServerResponse> saveLottery(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(LotteryDto.class)
                .flatMap(ele -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(saveLotteryUseCase.apply(ele), Response.class));
    }

    public Mono<ServerResponse> getLotteryAvailable(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(getLotteryAvailableUseCase.get(), Lottery.class);
    }

    public Mono<ServerResponse> getLottery(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(getAllLotteryUseCase.get(), Lottery.class);
    }
    public Mono<ServerResponse> getLotteryId(ServerRequest serverRequest) {
        Integer id = Integer.valueOf(serverRequest.pathVariable("id"));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(getLotteryIdUseCase.apply(id), LotteryDto.class);
    }
}
