package com.bingo.appbingo.infrastructure.driver_adapter.retreats;

import com.bingo.appbingo.domain.model.auth.Users;
import com.bingo.appbingo.domain.model.enums.StateTransaction;
import com.bingo.appbingo.domain.model.enums.TypeHistory;
import com.bingo.appbingo.domain.model.retreats.Retreats;
import com.bingo.appbingo.domain.model.retreats.gateway.RetreatsRepository;
import com.bingo.appbingo.domain.model.users.gateway.UsersRepository;
import com.bingo.appbingo.domain.model.userwallet.gateway.UserWalletRepository;
import com.bingo.appbingo.domain.model.utils.Response;
import com.bingo.appbingo.domain.model.utils.TypeStateResponses;
import com.bingo.appbingo.infrastructure.driver_adapter.auth.EmailService;
import com.bingo.appbingo.infrastructure.driver_adapter.exception.CustomException;
import com.bingo.appbingo.infrastructure.driver_adapter.exception.TypeStateResponse;
import com.bingo.appbingo.infrastructure.driver_adapter.helper.ReactiveAdapterOperations;
import com.bingo.appbingo.infrastructure.driver_adapter.retreats.mapper.RetreatsMapper;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Repository
public class RetreatsAdapterRepository extends ReactiveAdapterOperations<Retreats, RetreatsEntity, Integer, RetreatsReactiveRepository> implements RetreatsRepository {
    private final UserWalletRepository userWalletRepositoryAdapter;
    private final UsersRepository usersRepository;
    private final EmailService emailService;

    public RetreatsAdapterRepository(RetreatsReactiveRepository repository, UsersRepository usersRepository, UserWalletRepository userWalletRepositoryAdapter, EmailService emailService, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.mapBuilder(d, Retreats.RetreatsBuilder.class).build());
        this.userWalletRepositoryAdapter = userWalletRepositoryAdapter;
        this.usersRepository = usersRepository;
        this.emailService = emailService;
    }

    @Override
    public Mono<Retreats> retreatsId(Integer id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "No existe este id de transaccion", TypeStateResponse.Error)))
                .map(RetreatsMapper::retreatsEntityARetreats);
    }

    @Override
    public Mono<Response> moneyRequest(Retreats retreats, String token) {
       // var data= usersRepository.getUserIdToken(token)
              //  .map(ele->{

        //});
        return null;
    }

    @Override
    public Mono<Response> approveMoney(Integer id, String wallet, BigDecimal money) {
        return userWalletRepositoryAdapter.getWalletKey(wallet)
                .flatMap(walletKey -> {
                    //var total= walletKey.getBalance().
                    Mono<Users> usersMono = usersRepository.getUserId(walletKey.getUserId());
                    Mono<Void> decrement = userWalletRepositoryAdapter.decreaseBalanceBingoWinner(walletKey.getUserId(), money, TypeHistory.Retreats);
                    return Mono.zip(usersMono, decrement)
                            .flatMap(tuple -> {
                                Users user = tuple.getT1();
                                return emailService.sendNotification(user.getFullName(), user.getEmail())
                                        .then(updateState(id))
                                        .thenReturn(new Response(TypeStateResponses.Success, "Transaccion enviada"));

                            })
                            .onErrorResume(throwable -> Mono.just(new Response(TypeStateResponses.Error, "Error en la aprobación del dinero: " + throwable.getMessage())));
                });
    }

    @Override
    public Mono<Void> updateState(Integer id) {
        return repository.findById(id)
                .flatMap(ele -> {
                    ele.setUpdatedAt(LocalDateTime.now());
                    ele.setStateRetreats(StateTransaction.Completed);
                    return repository.save(ele);
                }).then();
    }

    public Flux<Retreats> getAllRetreats() {
        return repository.findAll()
                .filter(ele -> ele.getStateRetreats().equals(StateTransaction.Pending))
                .map(RetreatsMapper::retreatsEntityARetreats);
    }
}
