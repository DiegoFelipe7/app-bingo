package com.bingo.appbingo.infrastructure.driver_adapter.userwallet;

import com.bingo.appbingo.domain.model.enums.TypeHistory;
import com.bingo.appbingo.domain.model.history.gateway.PaymentHistoryRepository;
import com.bingo.appbingo.domain.model.users.gateway.UsersRepository;
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
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Repository
public class UserWalletRepositoryAdapter extends ReactiveAdapterOperations<UserWallet, UserWalletEntity, Integer, UserWalletReactiveRepository> implements UserWalletRepository {
    private final JwtProvider jwtProvider;
    private final UsersReactiveRepository usersReactiveRepository;
    private final PaymentHistoryRepository paymentHistoryRepository;

    public UserWalletRepositoryAdapter(UserWalletReactiveRepository repository , ObjectMapper mapper, JwtProvider jwtProvider,PaymentHistoryRepository paymentHistoryRepository, UsersReactiveRepository usersReactiveRepository) {
        super(repository, mapper, d -> mapper.mapBuilder(d, UserWallet.UserWalletBuilder.class).build());
        this.jwtProvider = jwtProvider;
        this.usersReactiveRepository = usersReactiveRepository;
        this.paymentHistoryRepository = paymentHistoryRepository;
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
    public Mono<UserWallet> getWalletUser(String token) {
        String username = jwtProvider.extractToken(token);
        return usersReactiveRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Ocurrió un error con el token", TypeStateResponse.Error)))
                .flatMap(user -> repository.findByUserId(user.getId()).map(UserWalletMapper::userWalletEntityAUserWallet));

    }

    @Override
    public Mono<Void> increaseBalance(Integer userId, BigDecimal quantity , TypeHistory typeHistory) {
        return repository.findByUserId(userId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Usuario invalido", TypeStateResponse.Error)))
                .flatMap(ele -> {
                    ele.setBalance(ele.getBalance().add(quantity));
                    ele.setUpdatedAt(LocalDateTime.now());
                    Mono<Void> savePaymentHistory = paymentHistoryRepository.saveHistory(userId, quantity , typeHistory);
                    Mono<UserWalletEntity> saveWallet = repository.save(ele);
                    return Mono.when(saveWallet,savePaymentHistory);
                }).then();
    }


    public Mono<Void> increaseBalanceWinner(Integer userId, BigDecimal quantity , TypeHistory typeHistory) {
        return repository.findByUserId(userId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Usuario invalido", TypeStateResponse.Error)))
                .flatMap(ele -> {
                    ele.setBingoWinnings(ele.getBingoWinnings().add(quantity));
                    ele.setUpdatedAt(LocalDateTime.now());
                    Mono<Void> savePaymentHistory = paymentHistoryRepository.saveHistory(userId, quantity , typeHistory);
                    Mono<UserWalletEntity> saveWallet = repository.save(ele);
                    return Mono.when(saveWallet,savePaymentHistory);
                }).then();
    }

    @Override
    public Mono<Void> decreaseBalance(Integer userId, BigDecimal quantity , TypeHistory typeHistory) {
        return repository.findByUserId(userId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Usuario invalido", TypeStateResponse.Error)))
                .flatMap(ele -> {
                    BigDecimal newBalance = ele.getBalance().subtract(quantity);
                    if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
                        return decreaseBalanceBingoWinner(userId,quantity,typeHistory);
                    }
                    ele.setBalance(newBalance);
                    ele.setUpdatedAt(LocalDateTime.now());
                    Mono<UserWalletEntity> saveWallet = repository.save(ele);
                    Mono<Void> savePaymentHistory = paymentHistoryRepository.saveHistory(userId, quantity , TypeHistory.Shopping);
                    return Mono.when(saveWallet,savePaymentHistory);
                }).then();
    }
    @Override
    public Mono<Void> decreaseBalanceBingoWinner(Integer userId, BigDecimal quantity , TypeHistory typeHistory ) {
        return repository.findByUserId(userId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Usuario invalido", TypeStateResponse.Error)))
                .flatMap(ele -> {
                    BigDecimal newBalance = ele.getBingoWinnings().subtract(quantity);
                    if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
                        return Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "El saldo es insuficciente", TypeStateResponse.Error));
                    }
                    ele.setBingoWinnings(newBalance);
                    ele.setUpdatedAt(LocalDateTime.now());
                    Mono<UserWalletEntity> saveWallet = repository.save(ele);
                    Mono<Void> savePaymentHistory = paymentHistoryRepository.saveHistory(userId, quantity , typeHistory);
                    return Mono.when(saveWallet,savePaymentHistory);
                }).then();
    }

    @Override
    public Mono<UserWallet> getWalletUserId(Integer id) {
        return repository.findByUserId(id)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST,"No existe un usuario con esta wallet",TypeStateResponse.Error)))
                .map(UserWalletMapper::userWalletEntityAUserWallet);
    }

    @Override
    public Mono<UserWallet> getWalletKey(String wallet) {
        return repository.findByWalletEqualsIgnoreCase(wallet)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST , "La wallet no existe" , TypeStateResponse.Error)))
                .map(UserWalletMapper::userWalletEntityAUserWallet);
    }

}
