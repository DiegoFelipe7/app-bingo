package com.bingo.appbingo.infrastructure.driver_adapter.retreats;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "retreats")
public class RetreatsEntity {
    @Id
    private Integer id;
    private Integer userWalletId;
    private BigDecimal price;
    private BigDecimal commissionPercentage;
    private String currency;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
