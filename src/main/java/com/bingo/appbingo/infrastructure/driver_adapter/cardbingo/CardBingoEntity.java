package com.bingo.appbingo.infrastructure.driver_adapter.cardbingo;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
public class CardBingoEntity {
    @Id
    private Integer id;
    private Integer userId;
    private String lotteryId;
}
