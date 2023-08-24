package com.bingo.appbingo.infrastructure.entry_points.api.lottery;

import com.bingo.appbingo.domain.model.lottery.Lottery;
import com.bingo.appbingo.domain.model.lottery.LotteryDto;
import com.bingo.appbingo.domain.model.round.Round;
import com.bingo.appbingo.domain.model.utils.Response;
import com.bingo.appbingo.domain.usecase.lottery.*;
import com.bingo.appbingo.domain.usecase.round.GetLotteryRoundUseCase;
import com.bingo.appbingo.domain.usecase.round.StartRoundUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final StartRoundUseCase startRoundUseCase;
    private final GetLotteryRoundUseCase getLotteryRoundUseCase;
    private final GetLotteryStarAdminUseCase getLotteryStarAdminUseCase;

    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Mono<ServerResponse> saveLottery(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(LotteryDto.class)
                .flatMap(ele -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(saveLotteryUseCase.apply(ele), Response.class));
    }

    public Mono<ServerResponse> getLotteryRound(ServerRequest serverRequest) {
        String lottery = serverRequest.pathVariable("lottery");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(getLotteryRoundUseCase.apply(lottery), Round.class);
    }

   // @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Mono<ServerResponse> getLotteryStartAdmin(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(getLotteryStarAdminUseCase.get(), LotteryDto.class);
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
        String id = serverRequest.pathVariable("id");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(getLotteryIdUseCase.apply(id), LotteryDto.class);
    }
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Mono<ServerResponse> startRound(ServerRequest serverRequest){
        String lottery = serverRequest.pathVariable("lottery");
        Integer id = Integer.valueOf(serverRequest.pathVariable("id"));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(startRoundUseCase.apply(lottery,id), Void.class);
    }

    public Mono<ServerResponse> stopRound(ServerRequest serverRequest){
        String lottery = serverRequest.pathVariable("lottery");
        Integer id = Integer.valueOf(serverRequest.pathVariable("id"));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(startRoundUseCase.apply(lottery,id), Void.class);
    }

}
