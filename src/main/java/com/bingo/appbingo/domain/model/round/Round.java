package com.bingo.appbingo.domain.model.round;

import com.bingo.appbingo.domain.model.enums.TypeLottery;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
    private List<String> balls;
    private Integer userWinner;
    private Boolean completed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
