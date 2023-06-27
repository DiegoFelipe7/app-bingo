package com.bingo.appbingo.domain.model.users.gateway;

import com.bingo.appbingo.domain.model.auth.Users;
import com.bingo.appbingo.domain.model.users.References;
import com.bingo.appbingo.domain.model.utils.Response;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Flux<References> getAllReferences(String token);
    Flux<References> getAllReferencesTeam(String token);
    Flux<Users> getAllUsers();
    Mono<Response> editUser(Users user);
    Mono<Response> saveWallet(String token, String wallet);
    Mono<Users> getUserId(String token);
}
