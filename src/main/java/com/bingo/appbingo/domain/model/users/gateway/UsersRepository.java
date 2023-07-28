package com.bingo.appbingo.domain.model.users.gateway;

import com.bingo.appbingo.domain.model.auth.Users;
import com.bingo.appbingo.domain.model.users.PanelUsers;
import com.bingo.appbingo.domain.model.users.References;
import com.bingo.appbingo.domain.model.utils.Response;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface UsersRepository {
    Flux<References> getAllReferences(String token);

    Flux<References> getAllReferencesTeam(String token);

    Flux<Users> getAllUsers();

    Mono<Response> editUser(Users user);

    Mono<Users> getUserIdToken(String token);
    Mono<Users> getUserId(Integer id);
    Mono<PanelUsers> panelUser(String token);

    Mono<Void> activateUserNetwork(Integer userId);

    Mono<Void> distributeCommission(Integer id , BigDecimal total);
}
