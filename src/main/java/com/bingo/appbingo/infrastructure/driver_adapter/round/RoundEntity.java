package com.bingo.appbingo.infrastructure.driver_adapter.round;

import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
@Table(name = "round")
public class RoundEntity {
    private Integer id;
    private String typeGame;
    private Integer numberRound;
    private BigDecimal award;
    private Integer userWinner;
    private Boolean completed;

    public RoundEntity(String typeGame, BigDecimal award) {
        this.typeGame = typeGame;
        this.award = award;
    }
}
