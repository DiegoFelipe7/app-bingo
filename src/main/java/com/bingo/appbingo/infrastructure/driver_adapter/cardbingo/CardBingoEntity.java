package com.bingo.appbingo.infrastructure.driver_adapter.cardbingo;

import com.bingo.appbingo.domain.model.cardbingo.BingoCard;
import com.bingo.appbingo.domain.model.cardbingo.CardBingo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    @Column("card")
    private List<BingoCard> card;
    private Integer round;
    private Boolean state;
    private LocalDateTime createdAt;



}
