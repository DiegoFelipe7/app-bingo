package com.bingo.appbingo.domain.model.transaction;

import com.bingo.appbingo.domain.model.enums.StateTransaction;
import com.bingo.appbingo.domain.model.enums.TypeTransaction;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ToString
public class Transaction {
    private Integer id;
    private String walletType;
    private String transaction;
    private BigDecimal price;
    private String currency;
    private String urlTransaction;
    private Integer userId;
    private StateTransaction stateTransaction;

    private TypeTransaction typeTransaction;
    private Boolean state;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Transaction(Integer id, String walletType, String transaction, BigDecimal price,String currency, String urlTransaction, StateTransaction stateTransaction, Boolean state , TypeTransaction typeTransaction, LocalDateTime createdAt) {
        this.id = id;
        this.walletType = walletType;
        this.transaction = transaction;
        this.price = price;
        this.currency=currency;
        this.urlTransaction = urlTransaction;
        this.stateTransaction = stateTransaction;
        this.state = state;
        this.typeTransaction=typeTransaction;
        this.createdAt=createdAt;
    }
}
