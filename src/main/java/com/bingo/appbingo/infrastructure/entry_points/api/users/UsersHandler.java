package com.bingo.appbingo.infrastructure.entry_points.api.users;

import com.bingo.appbingo.domain.model.auth.Users;
import com.bingo.appbingo.domain.model.users.References;
import com.bingo.appbingo.domain.model.utils.Response;
import com.bingo.appbingo.domain.usecase.users.*;
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
public class UsersHandler {
    private final GetUsersUseCase getUsersUseCase;
    private final GetUserIdUseCase getUserIdUseCase;
    private final EditUserUseCase editUserUseCase;
    private final SaveWalletUseCase saveWalletUseCase;
    private final ReferencesUseCase referencesUseCase;
    private final ReferenceTeamUseCase referenceTeamUseCase;

    public Mono<ServerResponse> references(ServerRequest serverRequest) {
        String token = serverRequest.headers().firstHeader("Authorization");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(referencesUseCase.apply(token), References.class);
    }

    public Mono<ServerResponse> referencesTeam(ServerRequest serverRequest) {
        String token = serverRequest.headers().firstHeader("Authorization");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(referenceTeamUseCase.apply(token), References.class);
    }

    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Mono<ServerResponse> getAllUsers(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(getUsersUseCase.get(), Users.class);

    }

    public Mono<ServerResponse> updateUser(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(Users.class)
                .flatMap(ele -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(editUserUseCase.apply(ele), Response.class));
    }



    public Mono<ServerResponse> saveWallet(ServerRequest serverRequest) {
        String token = serverRequest.headers().firstHeader("Authorization");
        return serverRequest.bodyToMono(Users.class)
                .flatMap(ele -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(saveWalletUseCase.apply(token, ele.getWallet()), Response.class));

    }
    public Mono<ServerResponse> getUserId(ServerRequest serverRequest){
        String token = serverRequest.headers().firstHeader("Authorization");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(getUserIdUseCase.apply(token),Users.class);
    }


}
