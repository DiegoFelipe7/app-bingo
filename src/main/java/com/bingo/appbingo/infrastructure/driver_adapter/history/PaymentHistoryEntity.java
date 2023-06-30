package com.bingo.appbingo.infrastructure.driver_adapter.history;

import com.bingo.appbingo.domain.model.enums.TypeHistory;
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
@Table(name = "payment_history")
public class PaymentHistoryEntity {
    @Id
    private Integer id;
    private String hash;
    private BigDecimal balance ;
    private String currency;
    private Integer userId;
    private TypeHistory typeHistory;
    private Boolean state;
    private LocalDateTime createdAt;
}
