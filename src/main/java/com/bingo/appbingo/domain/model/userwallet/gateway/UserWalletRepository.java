package com.bingo.appbingo.domain.model.userwallet.gateway;


import com.bingo.appbingo.domain.model.enums.TypeHistory;
import com.bingo.appbingo.domain.model.history.PaymentHistory;
import com.bingo.appbingo.domain.model.userwallet.UserWallet;
import com.bingo.appbingo.domain.model.utils.Response;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;


public interface UserWalletRepository {
    Mono<Response> saveWallet(String token , String wallet);
    Mono<UserWallet> getWalletUser(String token);

    Mono<Void> increaseBalance(Integer userId , BigDecimal quantity , TypeHistory typeHistory);

    Mono<Void> decreaseBalance(Integer userId , BigDecimal quantity);
}
