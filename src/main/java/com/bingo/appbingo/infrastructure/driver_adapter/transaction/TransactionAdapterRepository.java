package com.bingo.appbingo.infrastructure.driver_adapter.transaction;

import com.bingo.appbingo.domain.model.enums.StateTransaction;
import com.bingo.appbingo.domain.model.transaction.Transaction;
import com.bingo.appbingo.domain.model.transaction.TransactionDto;
import com.bingo.appbingo.domain.model.transaction.gateway.TransactionRepository;
import com.bingo.appbingo.domain.model.utils.Response;
import com.bingo.appbingo.domain.model.utils.TypeStateResponses;
import com.bingo.appbingo.infrastructure.driver_adapter.exception.CustomException;
import com.bingo.appbingo.infrastructure.driver_adapter.exception.TypeStateResponse;
import com.bingo.appbingo.infrastructure.driver_adapter.helper.ReactiveAdapterOperations;
import com.bingo.appbingo.infrastructure.driver_adapter.security.jwt.JwtProvider;
import com.bingo.appbingo.infrastructure.driver_adapter.transaction.mapper.TransactionMapper;
import com.bingo.appbingo.infrastructure.driver_adapter.users.UsersReactiveRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
public class TransactionAdapterRepository extends ReactiveAdapterOperations<Transaction, TransactionEntity, Integer, TransactionReactiveRepository> implements TransactionRepository {
    private final UsersReactiveRepository usersReactiveRepository;
    private final JwtProvider jwtProvider;

    public TransactionAdapterRepository(TransactionReactiveRepository repository, ObjectMapper mapper, UsersReactiveRepository usersReactiveRepository, JwtProvider jwtProvider) {
        super(repository, mapper, d -> mapper.mapBuilder(d, Transaction.TransactionBuilder.class).build());
        this.usersReactiveRepository = usersReactiveRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public Mono<Response> saveTransaction(Transaction transaction, String token) {
        String username = jwtProvider.extractToken(token);
        return usersReactiveRepository.findByUsername(username)
                .flatMap(ele -> {
                    transaction.setUserId(ele.getId());
                    transaction.setStateTransaction(StateTransaction.Pending);
                    return repository.save(TransactionMapper.transactionATransactionEntity(transaction))
                            .thenReturn(new Response(TypeStateResponses.Success, "Transacción enviada"));
                });
    }

    @Override
    public Mono<TransactionDto> getTransactionId(Integer id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST , "Error en el id de la transacción" , TypeStateResponse.Warning)))
                .flatMap(ele -> usersReactiveRepository.findById(ele.getId())
                        .map(data -> new TransactionDto(ele.getId(), ele.getWalletType(), ele.getTransaction(), ele.getPrice(), ele.getUrlTransaction(), ele.getStateTransaction(), ele.getState(), data.getUsername(), data.getEmail())));
    }

    @Override
    public Flux<TransactionDto> getAllTransaction() {
        return repository.findAll()
                .flatMap(ele -> usersReactiveRepository.findById(ele.getId())
                        .map(data -> new TransactionDto(ele.getId(), ele.getWalletType(), ele.getTransaction(), ele.getPrice(), ele.getUrlTransaction(), ele.getStateTransaction(), ele.getState(), data.getUsername(), data.getEmail())));
    }

    @Override
    public Mono<Response> validateTransaction(String transaction) {
        return repository.findByTransaction(transaction)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Ocurrio un error en la seleccion de la transacción", TypeStateResponse.Error)))
                .flatMap(ele->{
                    ele.setId(ele.getId());
                    ele.setState(false);
                    ele.setStateTransaction(StateTransaction.Completed);
                    return repository.save(ele).thenReturn(new Response());
                });
    }
}
