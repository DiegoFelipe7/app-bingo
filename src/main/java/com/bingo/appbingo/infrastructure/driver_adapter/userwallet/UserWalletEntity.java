package com.bingo.appbingo.infrastructure.driver_adapter.userwallet;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@Table(name = "user_wallet")
public class UserWalletEntity {
    @Id
    private Integer id;
    private BigDecimal balance;
    private Integer userId;
    private String wallet;
    private Boolean state;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserWalletEntity(BigDecimal balance, Integer userId) {
        this.balance = balance;
        this.userId = userId;
    }

    public void increaseBalance(BigDecimal amount) {
        balance = balance.add(amount);
    }
    public void decreaseBalance(BigDecimal amount) {
        balance = balance.subtract(amount);
    }
}
