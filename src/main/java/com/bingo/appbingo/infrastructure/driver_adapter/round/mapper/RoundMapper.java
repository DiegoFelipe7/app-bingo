package com.bingo.appbingo.infrastructure.driver_adapter.round.mapper;

import com.bingo.appbingo.domain.model.round.Round;
import com.bingo.appbingo.infrastructure.driver_adapter.round.RoundEntity;

public class RoundMapper {
    private RoundMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static Round roundEntityARound(RoundEntity roundEntity) {
        return Round.builder()
                .id(roundEntity.getId())
                .typeGame(roundEntity.getTypeGame())
                .numberRound(roundEntity.getNumberRound())
                .award(roundEntity.getAward())
                .userWinner(roundEntity.getUserWinner())
                .completed(roundEntity.getCompleted())
                .createdAt(roundEntity.getCreatedAt())
                .updatedAt(roundEntity.getUpdatedAt())
                .build();
    }

    public static RoundEntity roundARoundEntity(Round round) {
        return RoundEntity.builder()
                .id(round.getId())
                .typeGame(round.getTypeGame())
                .numberRound(round.getNumberRound())
                .award(round.getAward())
                .userWinner(round.getUserWinner())
                .completed(round.getCompleted())
                .createdAt(round.getCreatedAt())
                .updatedAt(round.getUpdatedAt())
                .build();
    }

    public static RoundEntity roundEntity(Round round) {
        return  RoundEntity.builder()
                .id(round.getId())
                .numberRound(round.getNumberRound())
                .idLottery(round.getIdLottery())
                .typeGame(round.getTypeGame())
                .award(round.getAward())
                .createdAt(round.getCreatedAt())
                .updatedAt(round.getUpdatedAt())
                .build();
    }
}
