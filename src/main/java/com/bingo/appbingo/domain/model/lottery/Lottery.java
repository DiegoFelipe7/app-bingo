package com.bingo.appbingo.domain.model.lottery;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
public class Lottery {
    private Integer id;
    private String key;
    private LocalDateTime createdAt;
    private LocalDateTime startDate;
    private Integer numberOfRounds;
    private List<Integer> userGame;
    private Boolean state;
}
