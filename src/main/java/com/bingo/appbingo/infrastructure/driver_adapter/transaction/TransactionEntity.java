package com.bingo.appbingo.infrastructure.driver_adapter.transaction;

import com.bingo.appbingo.domain.model.enums.StateTransaction;
import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "transaction")
public class TransactionEntity {
    private Integer id;
    private String walletType;
    private String transaction;
    private BigDecimal price;
    private String urlTransaction;
    private Integer userId;
    private StateTransaction stateTransaction;
    private Boolean state;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;


}
