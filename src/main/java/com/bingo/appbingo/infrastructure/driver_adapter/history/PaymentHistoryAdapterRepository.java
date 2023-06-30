package com.bingo.appbingo.infrastructure.driver_adapter.history;

import com.bingo.appbingo.domain.model.enums.TypeHistory;
import com.bingo.appbingo.domain.model.history.PaymentHistory;
import com.bingo.appbingo.domain.model.history.gateway.PaymentHistoryRepository;
import com.bingo.appbingo.infrastructure.driver_adapter.helper.ReactiveAdapterOperations;
import com.bingo.appbingo.infrastructure.driver_adapter.helper.Utils;
import com.bingo.appbingo.infrastructure.driver_adapter.history.mapper.PaymentHistoryMapper;
import com.bingo.appbingo.infrastructure.driver_adapter.security.jwt.JwtProvider;
import com.bingo.appbingo.infrastructure.driver_adapter.users.UsersReactiveRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Repository
public class PaymentHistoryAdapterRepository extends ReactiveAdapterOperations<PaymentHistory, PaymentHistoryEntity , Integer , PaymentHistoryReactiveRepository> implements PaymentHistoryRepository {
    private final UsersReactiveRepository usersReactiveRepository;
    private final JwtProvider jwtProvider;
    public PaymentHistoryAdapterRepository(PaymentHistoryReactiveRepository repository,UsersReactiveRepository usersReactiveRepository , JwtProvider jwtProvider, ObjectMapper mapper) {
        super(repository, mapper, d->mapper.mapBuilder(d,PaymentHistory.PaymentHistoryBuilder.class).build());
        this.usersReactiveRepository=usersReactiveRepository;
        this.jwtProvider =jwtProvider;
    }

    @Override
    public Mono<Void> saveHistory(Integer userId, BigDecimal balance) {
        PaymentHistory history= new PaymentHistory(Utils.uid(), balance,userId, TypeHistory.Transaction);
        return repository.save(PaymentHistoryMapper.paymentHistoryAPaymentHistoryEntity(history)).then();

    }

    @Override
    public Flux<PaymentHistory> listPayment(String token) {
        String username = jwtProvider.extractToken(token);
        return usersReactiveRepository.findByUsername(username)
                .flatMapMany(ele -> repository.findAll()
                        .filter(res -> res.getUserId().equals(ele.getId()))
                        .map(PaymentHistoryMapper::paymentHistoryEntityAPaymentHistory));

    }

    @Override
    public Flux<PaymentHistory> filterPaymentHistory(String type) {
        return repository.findAll().filter(ele->ele.getTypeHistory().equals(type))
                .map(PaymentHistoryMapper::paymentHistoryEntityAPaymentHistory);
    }


}
