package com.bingo.appbingo.domain.model.userwallet;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class UserWallet {
    private Integer id;
    private String red;
    private BigDecimal balance;
    private Integer userId;
    private String wallet;
    private Boolean state;
    private String currency;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;




}
