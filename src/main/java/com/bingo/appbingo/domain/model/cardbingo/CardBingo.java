package com.bingo.appbingo.domain.model.cardbingo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
public class CardBingo {
    private Integer id;
    private Integer userId;
    private String lotteryId;
}
