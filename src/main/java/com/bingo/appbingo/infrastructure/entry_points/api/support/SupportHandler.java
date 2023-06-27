package com.bingo.appbingo.infrastructure.entry_points.api.support;

import com.bingo.appbingo.domain.model.support.Support;
import com.bingo.appbingo.domain.model.utils.Response;
import com.bingo.appbingo.domain.usecase.support.*;
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
public class SupportHandler {
    private final GetAllSupportUseCase getAllSupportUseCase;
    private final GetAllSupportIdUseCase getAllSupportIdUseCase;
    private final GetAllSupportUserUseCase getAllSupportUserUseCase;
    private final SaveSupportUseCase saveSupportUseCase;
    private final EditSupportUseCase editSupportUseCase;
    public Mono<ServerResponse> getAllSupport(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(getAllSupportUseCase.get(), Support.class);
    }

    public Mono<ServerResponse> getAllSupportId(ServerRequest serverRequest){
        Integer id = Integer.valueOf(serverRequest.pathVariable("id"));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(getAllSupportIdUseCase.apply(id), Support.class);
    }

    public Mono<ServerResponse> getAllSupportUsers(ServerRequest serverRequest){
        String token = serverRequest.headers().firstHeader("Authorization");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(getAllSupportUserUseCase.apply(token), Support.class);
    }


    public Mono<ServerResponse> registrationSupport(ServerRequest serverRequest) {
        String token = serverRequest.headers().firstHeader("Authorization");
        return serverRequest
                .bodyToMono(Support.class)
                .flatMap(ele -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(saveSupportUseCase.apply(ele, token), Response.class));
    }


    public Mono<ServerResponse> editSupport(ServerRequest serverRequest) {
        Integer id = Integer.valueOf(serverRequest.pathVariable("id"));
        return serverRequest
                .bodyToMono(Support.class)
                .flatMap(ele -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(editSupportUseCase.apply(ele, id), Response.class));
    }

}
