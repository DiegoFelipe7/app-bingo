package com.bingo.appbingo.infrastructure.driver_adapter.transaction;

import com.bingo.appbingo.domain.model.enums.StateTransaction;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
@Table(name = "transaction")
public class TransactionEntity {
    @Id
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


}
