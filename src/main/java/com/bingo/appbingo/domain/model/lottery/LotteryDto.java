package com.bingo.appbingo.domain.model.lottery;

import com.bingo.appbingo.domain.model.round.Round;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
public class LotteryDto {
    private Integer id;
    private String key;
    private LocalDateTime createdAt;
    private String  startDate;
    private Integer numberOfRounds;
    private List<Round> rounds;
    private Boolean state;

}
