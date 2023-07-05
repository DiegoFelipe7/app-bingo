package com.bingo.appbingo.domain.model.round;

import com.bingo.appbingo.domain.model.enums.TypeLottery;
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
    private Integer idLottery;
    private TypeLottery typeGame;
    private Integer numberRound;
    private BigDecimal award;
    private Integer userWinner;
    private Boolean completed;


}
