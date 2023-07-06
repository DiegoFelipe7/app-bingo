package com.bingo.appbingo.infrastructure.driver_adapter.cardbingo;

import com.bingo.appbingo.domain.model.cardbingo.BingoBalls;
import com.bingo.appbingo.infrastructure.driver_adapter.bingoBalls.BingoBallsEntity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
@Table(name = "card_bingo")
public class CardBingoEntity {
    @Id
    private Integer id;
    private String key;
    private Integer userId;
    private Integer lotteryId;
    private Integer round;
    private Boolean state;
    private LocalDateTime createdAt;



}
