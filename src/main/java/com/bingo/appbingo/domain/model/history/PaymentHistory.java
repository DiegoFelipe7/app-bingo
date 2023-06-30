package com.bingo.appbingo.domain.model.history;

import com.bingo.appbingo.domain.model.enums.TypeHistory;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
public class PaymentHistory {
    private Integer id;
    private String hash;
    private BigDecimal balance ;
    private String currency;
    private Integer userId;
    private TypeHistory typeHistory;
    private Boolean state;
    private LocalDateTime createdAt;

    public PaymentHistory(String hash, BigDecimal balance, Integer userId, TypeHistory typeHistory) {
        this.hash = hash;
        this.balance = balance;
        this.userId = userId;
        this.typeHistory = typeHistory;
    }
}
