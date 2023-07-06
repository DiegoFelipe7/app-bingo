package com.bingo.appbingo.domain.model.cardbingo;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
public class CardBingo {
    private Integer id;
    private String key;
    private Integer userId;
    private Integer lotteryId;
    private Integer round;
    private Boolean state;
    private LocalDateTime createdAt;


}
