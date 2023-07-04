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
    private String red;
    private BigDecimal balance;
    private Integer userId;
    private String wallet;
    private Boolean state;
    private String currency;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserWalletEntity(String red,BigDecimal balance) {
        this.red=red;
        this.balance = balance;
    }

}
