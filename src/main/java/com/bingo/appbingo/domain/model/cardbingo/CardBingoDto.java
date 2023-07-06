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
public class CardBingoDto {
    private Integer id;
    private String key;
    private Integer userId;
    private Integer lotteryId;
    private List<BingoBalls> card;
    private Integer round;
    private Boolean state;
    private LocalDateTime createdAt;


    public CardBingoDto(String key, List<BingoBalls> card) {
        this.key = key;
        this.card = card;
    }
}
