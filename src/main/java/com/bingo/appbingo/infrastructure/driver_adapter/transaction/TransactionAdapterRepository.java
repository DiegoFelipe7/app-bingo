package com.bingo.appbingo.infrastructure.driver_adapter.transaction;

import com.bingo.appbingo.domain.model.enums.StateTransaction;
import com.bingo.appbingo.domain.model.history.gateway.PaymentHistoryRepository;
import com.bingo.appbingo.domain.model.transaction.Transaction;
import com.bingo.appbingo.domain.model.transaction.TransactionDto;
import com.bingo.appbingo.domain.model.transaction.gateway.TransactionRepository;
import com.bingo.appbingo.domain.model.userwallet.gateway.UserWalletRepository;
import com.bingo.appbingo.domain.model.utils.Response;
import com.bingo.appbingo.domain.model.utils.TypeStateResponses;
import com.bingo.appbingo.infrastructure.driver_adapter.auth.EmailService;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Repository
public class TransactionAdapterRepository extends ReactiveAdapterOperations<Transaction, TransactionEntity, Integer, TransactionReactiveRepository> implements TransactionRepository {
    private final UsersReactiveRepository usersReactiveRepository;
    private final UserWalletRepository userWalletRepository;
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final JwtProvider jwtProvider;
    private final EmailService emailService;

    public TransactionAdapterRepository(TransactionReactiveRepository repository, UserWalletRepository userWalletRepository, PaymentHistoryRepository paymentHistoryRepository, ObjectMapper mapper, UsersReactiveRepository usersReactiveRepository, JwtProvider jwtProvider, EmailService emailService) {
        super(repository, mapper, d -> mapper.mapBuilder(d, Transaction.TransactionBuilder.class).build());
        this.usersReactiveRepository = usersReactiveRepository;
        this.userWalletRepository = userWalletRepository;
        this.paymentHistoryRepository=paymentHistoryRepository;
        this.jwtProvider = jwtProvider;
        this.emailService = emailService;
    }

    @Override
    public Mono<TransactionDto> getTransactionId(Integer id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Error en el id de la transacción", TypeStateResponse.Warning)))
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
    public Mono<Response> saveTransaction(Transaction transaction, String token) {
        String username = jwtProvider.extractToken(token);
        return repository.findByTransactionIgnoreCase(transaction.getTransaction())
                .flatMap(existingTransaction -> Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Esta transacción ya se encuentra registrada!", TypeStateResponse.Warning)))
                .switchIfEmpty(usersReactiveRepository.findByUsername(username)
                        .flatMap(user -> {
                            transaction.setUserId(user.getId());
                            transaction.setStateTransaction(StateTransaction.Pending);
                            return repository.save(TransactionMapper.transactionATransactionEntity(transaction));
                        })
                        .flatMap(savedTransaction -> Mono.just(new Response(TypeStateResponses.Success, "Transacción enviada")))
                ).cast(Response.class);
    }


    @Override
    public Mono<Response> validateTransaction(String hash, Transaction transaction) {
        return repository.findByTransactionIgnoreCase(hash)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Ocurrio un error en la selección de la transacción", TypeStateResponse.Error)))
                .flatMap(ele -> {
                    if (ele.getStateTransaction().equals(StateTransaction.Completed)) {
                        return Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "La transaccion ya se encuentra activa!", TypeStateResponse.Error));
                    }
                    ele.setId(transaction.getId());
                    ele.setPrice(transaction.getPrice());
                    ele.setState(true);
                    ele.setUpdatedAt(LocalDateTime.now());
                    ele.setStateTransaction(StateTransaction.Completed);
                    return repository.save(ele)
                            .flatMap(res -> {
                                Mono<Void> updateUserWallet = userWalletRepository.increaseBalance(res.getUserId(), res.getPrice());
                                Mono<Void> savePaymentHistory = paymentHistoryRepository.saveHistory(res.getUserId(), res.getPrice());
                                return Mono.when(updateUserWallet, savePaymentHistory).log();
                            })
                            .thenReturn(new Response(TypeStateResponses.Success, "Transacción activada!"));
                });
    }


    @Override
    public Mono<Response> invalidTransaction(String transaction) {
        return repository.findByTransactionIgnoreCase(transaction)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Ocurrio un error en la seleccion de la transacción", TypeStateResponse.Error)))
                .flatMap(transactionEntity -> usersReactiveRepository.findById(transactionEntity.getId())
                        .flatMap(user -> {
                            transactionEntity.setId(transactionEntity.getId());
                            transactionEntity.setState(false);
                            transactionEntity.setPrice(BigDecimal.ZERO);
                            transactionEntity.setStateTransaction(StateTransaction.Invalid);
                            return repository.save(transactionEntity)
                                    .flatMap(savedTransaction -> emailService.invalidTransaction(user.getFullName(), user.getEmail()))
                                    .thenReturn(new Response(TypeStateResponses.Success, "Transacción invalidada"));
                        })
                );
    }
}
