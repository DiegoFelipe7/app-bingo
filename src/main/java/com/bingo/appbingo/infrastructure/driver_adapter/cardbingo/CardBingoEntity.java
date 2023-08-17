package com.bingo.appbingo.infrastructure.driver_adapter.cardbingo;

import com.bingo.appbingo.domain.model.cardbingo.BingoBalls;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
@Document(value = "card_bingo")
public class CardBingoEntity {
    @Id
    private String id;
    private String key;
    private Integer userId;
    private String lotteryId;
    private List<BingoBalls> card;
    private Integer round;
    private Boolean state;
    private LocalDateTime createdAt;



}
