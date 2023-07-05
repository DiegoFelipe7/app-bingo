package com.bingo.appbingo.domain.model.transaction;
import com.bingo.appbingo.domain.model.enums.StateTransaction;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class TransactionDto extends Transaction{
    private String username;
    private String email;

    public TransactionDto(Integer id, String walletType, String transaction, BigDecimal price, String currency, String urlTransaction, StateTransaction stateTransaction, Boolean state, LocalDateTime createdAt,String username, String email) {
        super(id, walletType, transaction, price, currency, urlTransaction, stateTransaction, state,createdAt);
        this.username = username;
        this.email = email;
    }
}
