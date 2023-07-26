package com.bingo.appbingo.domain.model.retreats;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Retreats {
    private Integer id;
    private Integer userWalletId;
    private BigDecimal price;
    private BigDecimal commissionPercentage;
    private String currency;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
