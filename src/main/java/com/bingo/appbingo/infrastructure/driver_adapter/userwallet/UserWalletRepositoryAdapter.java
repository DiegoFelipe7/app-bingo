package com.bingo.appbingo.infrastructure.driver_adapter.userwallet;

import com.bingo.appbingo.domain.model.userwallet.UserWallet;
import com.bingo.appbingo.domain.model.userwallet.gateway.UserWalletRepository;
import com.bingo.appbingo.domain.model.utils.Response;
import com.bingo.appbingo.domain.model.utils.TypeStateResponses;
import com.bingo.appbingo.infrastructure.driver_adapter.exception.CustomException;
import com.bingo.appbingo.infrastructure.driver_adapter.exception.TypeStateResponse;
import com.bingo.appbingo.infrastructure.driver_adapter.helper.ReactiveAdapterOperations;
import com.bingo.appbingo.infrastructure.driver_adapter.security.jwt.JwtProvider;
import com.bingo.appbingo.infrastructure.driver_adapter.users.UsersReactiveRepository;
import com.bingo.appbingo.infrastructure.driver_adapter.userwallet.mapper.UserWalletMapper;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Repository
public class UserWalletRepositoryAdapter extends ReactiveAdapterOperations<UserWallet, UserWalletEntity, Integer, UserWalletReactiveRepository> implements UserWalletRepository {
    private final JwtProvider jwtProvider;
    private final UsersReactiveRepository usersReactiveRepository;

    public UserWalletRepositoryAdapter(UserWalletReactiveRepository repository, ObjectMapper mapper, JwtProvider jwtProvider, UsersReactiveRepository usersReactiveRepository) {
        super(repository, mapper, d -> mapper.mapBuilder(d, UserWallet.UserWalletBuilder.class).build());
        this.jwtProvider = jwtProvider;
        this.usersReactiveRepository = usersReactiveRepository;
    }


    public Mono<Response> saveWallet(String token, String wallet) {
        String username = jwtProvider.extractToken(token);
        return repository.findByWalletEqualsIgnoreCase(wallet)
                .flatMap(ele -> Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Lo sentimos, esta wallet ya está registrada", TypeStateResponse.Error)))
                .switchIfEmpty(usersReactiveRepository.findByUsername(username)
                        .flatMap(data -> repository.findByUserId(data.getId())
                                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, "Usuario no encontrado", TypeStateResponse.Error)))
                                .flatMap(res -> {
                                    res.setWallet(wallet);
                                    return repository.save(res);
                                })
                                .thenReturn(new Response(TypeStateResponses.Success, "Billetera Registrada"))
                        )
                )
                .onErrorResume(data -> Mono.just(new Response(TypeStateResponses.Error, data.getMessage())))
                .cast(Response.class);
    }


    @Override
    public Mono<Boolean> existWallet(String token) {
        String username = jwtProvider.extractToken(token);
        return usersReactiveRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Ocurrió un error con el token", TypeStateResponse.Error)))
                .flatMap(user -> repository.findByUserId(user.getId()))
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Ocurrio un error en la creacion de la billetera del usuario", TypeStateResponse.Error)))
                .map(valid -> valid.getWallet() != null);

    }

    @Override
    public Mono<Void> increaseBalance(Integer userId, BigDecimal quantity) {
       return repository.findByUserId(userId)
               .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST,"Usuario invalido" , TypeStateResponse.Error)))
                .flatMap(ele -> {
                    ele.setBalance(ele.getBalance().add(quantity));
                    ele.setUpdatedAt(LocalDateTime.now());
                    return repository.save(ele);
                }).then();
    }

    @Override
    public Mono<Void> decreaseBalance(Integer userId, BigDecimal quantity) {
        return repository.findByUserId(userId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST,"Usuario invalido" , TypeStateResponse.Error)))
                .flatMap(ele -> {
                    ele.setBalance(ele.getBalance().subtract(quantity));
                    ele.setUpdatedAt(LocalDateTime.now());
                    return repository.save(ele);
                }).then();
    }
}
