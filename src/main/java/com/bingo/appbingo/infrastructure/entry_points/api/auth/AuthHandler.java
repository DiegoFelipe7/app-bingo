package com.bingo.appbingo.infrastructure.entry_points.api.auth;

import com.bingo.appbingo.domain.model.auth.Login;
import com.bingo.appbingo.domain.model.auth.Token;
import com.bingo.appbingo.domain.model.auth.Users;
import com.bingo.appbingo.domain.model.utils.Response;
import com.bingo.appbingo.domain.usecase.auth.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthHandler {

    private final LoginUseCase loginUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final RecoverPasswordUseCase recoverPasswordUseCase;
    private final PasswordChangeUseCase passwordChangeUseCase;
    private final TokenValidationUseCase tokenValidationUseCase;
    private final ValidateTokenUseCase validateTokenUseCase;
    private final ActivateAccountUseCase activateAccountUseCase;

    public Mono<ServerResponse> login(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Login.class)
                .flatMap(ele -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(loginUseCase.apply(ele), Token.class));

    }

    public Mono<ServerResponse> create(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Users.class)
                .flatMap(ele -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(createUserUseCase.apply(ele), Response.class));
    }

    public Mono<ServerResponse> passwordRecovery(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Login.class)
                .flatMap(ele -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(recoverPasswordUseCase.apply(ele), Response.class));
    }

    public Mono<ServerResponse> validateToken(ServerRequest serverRequest) {
        String token = serverRequest.pathVariable("token");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(tokenValidationUseCase.apply(token), Boolean.class);
    }

    public Mono<ServerResponse> passwordChange(ServerRequest serverRequest) {
        String token = serverRequest.pathVariable("token");
        return serverRequest.bodyToMono(Login.class)
                .flatMap(ele -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(passwordChangeUseCase.apply(token, ele), Response.class));
    }

    public Mono<ServerResponse> validateBearerToken(ServerRequest serverRequest) {
        String token = serverRequest.headers().firstHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            return ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(validateTokenUseCase.apply(token), Boolean.class);
        }
        return null;
    }
    public Mono<ServerResponse> activateAccount(ServerRequest serverRequest) {
        String token = serverRequest.pathVariable("token");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(activateAccountUseCase.apply(token), Response.class);
    }






}
