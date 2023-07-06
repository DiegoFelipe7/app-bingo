package com.bingo.appbingo.infrastructure.driver_adapter.cardbingo.mapper;

import com.bingo.appbingo.domain.model.cardbingo.CardBingo;
import com.bingo.appbingo.domain.model.cardbingo.CardBingoDto;
import com.bingo.appbingo.infrastructure.driver_adapter.cardbingo.CardBingoEntity;



public class CardBingoMapper {

    private CardBingoMapper() {
        throw new IllegalStateException("Utility class");
    }
    public static CardBingo cardBingoEntityACardBingo(CardBingoEntity cardBingo){
        return CardBingo.builder()
                .id(cardBingo.getId())
                .key(cardBingo.getKey())
                .userId(cardBingo.getUserId())
                .lotteryId(cardBingo.getLotteryId())
                .round(cardBingo.getRound())
                .state(cardBingo.getState())
                .createdAt(cardBingo.getCreatedAt())
                .build();
    }

    public static CardBingoEntity cardBingoACardBingoEntity(CardBingo cardBingo){
        return CardBingoEntity.builder()
                .id(cardBingo.getId())
                .key(cardBingo.getKey())
                .userId(cardBingo.getUserId())
                .lotteryId(cardBingo.getLotteryId())
                .round(cardBingo.getRound())
                .state(cardBingo.getState())
                .createdAt(cardBingo.getCreatedAt())
                .build();
    }

    public static CardBingoEntity cardBingoDtoACardBingoEntity(CardBingoDto cardBingo){
        return CardBingoEntity.builder()
                .id(cardBingo.getId())
                .key(cardBingo.getKey())
                .userId(cardBingo.getUserId())
                .lotteryId(cardBingo.getLotteryId())
                .round(cardBingo.getRound())
                .state(cardBingo.getState())
                .createdAt(cardBingo.getCreatedAt())
                .build();
    }



}
