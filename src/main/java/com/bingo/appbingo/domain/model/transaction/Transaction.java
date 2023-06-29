package com.bingo.appbingo.domain.model.transaction;

import com.bingo.appbingo.domain.model.enums.StateTransaction;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Transaction {
    private Integer id;
    private String walletType;
    private String transaction;
    private BigDecimal price;
    private String currency;
    private String urlTransaction;
    private Integer userId;
    private StateTransaction stateTransaction;
    private Boolean state;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Transaction(Integer id, String walletType, String transaction, BigDecimal price, String urlTransaction, StateTransaction stateTransaction, Boolean state) {
        this.id = id;
        this.walletType = walletType;
        this.transaction = transaction;
        this.price = price;
        this.urlTransaction = urlTransaction;
        this.stateTransaction = stateTransaction;
        this.state = state;
    }
}
