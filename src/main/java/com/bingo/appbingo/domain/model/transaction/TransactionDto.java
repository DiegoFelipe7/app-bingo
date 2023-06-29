package com.bingo.appbingo.domain.model.transaction;
import com.bingo.appbingo.domain.model.enums.StateTransaction;
import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class TransactionDto extends Transaction{
    private String username;
    private String email;

    public TransactionDto(Integer id, String walletType, String transaction, BigDecimal price, String urlTransaction, StateTransaction stateTransaction, Boolean state, String username, String email) {
        super(id, walletType, transaction, price, urlTransaction, stateTransaction, state);
        this.username = username;
        this.email = email;
    }
}
