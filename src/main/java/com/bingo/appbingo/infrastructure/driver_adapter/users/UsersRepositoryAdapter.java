package com.bingo.appbingo.infrastructure.driver_adapter.users;

import com.bingo.appbingo.domain.model.auth.Users;
import com.bingo.appbingo.domain.model.users.References;
import com.bingo.appbingo.domain.model.users.gateway.UserRepository;
import com.bingo.appbingo.domain.model.utils.Response;
import com.bingo.appbingo.domain.model.utils.TypeStateResponses;
import com.bingo.appbingo.infrastructure.driver_adapter.auth.UsersEntity;
import com.bingo.appbingo.infrastructure.driver_adapter.exception.CustomException;
import com.bingo.appbingo.infrastructure.driver_adapter.exception.TypeStateResponse;
import com.bingo.appbingo.infrastructure.driver_adapter.helper.ReactiveAdapterOperations;
import com.bingo.appbingo.infrastructure.driver_adapter.security.jwt.JwtProvider;
import com.bingo.appbingo.infrastructure.driver_adapter.users.mapper.UserMapper;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Repository
public class UsersRepositoryAdapter extends ReactiveAdapterOperations<Users , UsersEntity, Integer , UsersReactiveRepository> implements UserRepository {
    private final JwtProvider jwtProvider;
    public UsersRepositoryAdapter(UsersReactiveRepository repository,ObjectMapper mapper , JwtProvider jwtProvider) {
        super(repository, mapper, d -> mapper.mapBuilder(d, Users.UsersBuilder.class).build());
        this.jwtProvider=jwtProvider;
    }

    @Override
    public Flux<References> getAllReferences(String token) {
        String userName = jwtProvider.extractToken(token);
        return repository.findByUsername(userName)
                .flatMapMany(user -> repository.findAll()
                        .filter(ele -> Objects.equals(ele.getParentId(), user.getId()))
                        .filter(ele -> !ele.getUsername().equals(userName))
                        .map(data -> new References(data.getFullName(), data.getPhone(), data.getUsername(), data.getCreatedAt())));


    }

    @Override
    public Flux<References> getAllReferencesTeam(String token) {
        String userName = jwtProvider.extractToken(token);
        return repository.findUserAndDescendantsTeam(userName)
                .map(data -> new References(data.getFullName(), data.getLevel(), data.getUsername(), data.getCreatedAt()));

    }

    @Override
    public Flux<Users> getAllUsers() {
        return repository.findAll().map(UserMapper::usersEntityAUsers);
    }

    @Override
    public Mono<Response> editUser(Users user) {
        return repository.findByEmail(user.getEmail())
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Se ha producido un error, inténtelo de nuevo.", TypeStateResponse.Warning)))
                .flatMap(ele -> {
                    ele.setId(ele.getId());
                    ele.setPhoto(user.getPhoto());
                    ele.setPhone(user.getPhone());
                    ele.setCountry(user.getCountry());
                    ele.setCity(user.getCity());
                    return repository.save(ele)
                            .thenReturn(new Response(TypeStateResponses.Success, "Datos actualizados"));
                });
    }



    @Override
    public Mono<Users> getUserId(String token) {
        String username = jwtProvider.extractToken(token);
        return repository.findByUsername(username)
                .map(UserMapper::usersEntityAUsers);
    }
}
