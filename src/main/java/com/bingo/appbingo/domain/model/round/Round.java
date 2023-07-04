package com.bingo.appbingo.domain.model.round;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
public class Round {
    private Integer id;
    private String typeGame;
    private Integer numberRound;
    private BigDecimal award;
    private Integer userWinner;
    private Boolean completed;

    public Round(String typeGame, BigDecimal award) {
        this.typeGame = typeGame;
        this.award = award;
    }
}
