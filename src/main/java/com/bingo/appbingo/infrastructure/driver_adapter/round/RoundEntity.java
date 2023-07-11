package com.bingo.appbingo.infrastructure.driver_adapter.round;

import com.bingo.appbingo.domain.model.enums.TypeLottery;
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
@Table(name = "round")
public class RoundEntity {
    @Id
    private Integer id;
    private Integer idLottery;
    private TypeLottery typeGame;
    private Integer numberRound;
    private BigDecimal award;
    private Integer userWinner;
    private Boolean completed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;



}
