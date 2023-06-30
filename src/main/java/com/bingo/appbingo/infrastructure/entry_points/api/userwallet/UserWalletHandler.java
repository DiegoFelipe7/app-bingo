package com.bingo.appbingo.infrastructure.entry_points.api.userwallet;

import com.bingo.appbingo.domain.model.auth.Users;
import com.bingo.appbingo.domain.model.userwallet.UserWallet;
import com.bingo.appbingo.domain.model.utils.Response;
import com.bingo.appbingo.domain.usecase.userwallet.ExistsWalletUseCase;
import com.bingo.appbingo.domain.usecase.userwallet.IncreaseBalanceUseCase;
import com.bingo.appbingo.domain.usecase.userwallet.SaveWalletUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserWalletHandler {
    private final SaveWalletUseCase saveWalletUseCase;
    private final ExistsWalletUseCase existsWalletUseCase;
    private final IncreaseBalanceUseCase increaseBalanceUseCase;

    public Mono<ServerResponse> saveWallet(ServerRequest serverRequest) {
        String token = serverRequest.headers().firstHeader("Authorization");
        return serverRequest.bodyToMono(UserWallet.class)
                .flatMap(ele -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(saveWalletUseCase.apply(token, ele.getWallet()), Response.class));

    }

    public Mono<ServerResponse> existWallet(ServerRequest serverRequest){
        String token = serverRequest.headers().firstHeader("Authorization");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(existsWalletUseCase.apply(token), Boolean.class);
    }

    public Mono<ServerResponse> increaseBalance(ServerRequest serverRequest){
        String token = serverRequest.headers().firstHeader("Authorization");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(increaseBalanceUseCase.apply(30, BigDecimal.valueOf(25)), Void.class);
    }

    public Mono<ServerResponse> decreaseBalance(ServerRequest serverRequest){
        String token = serverRequest.headers().firstHeader("Authorization");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(existsWalletUseCase.apply(token), Boolean.class);
    }

}
