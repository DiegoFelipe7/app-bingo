package com.bingo.appbingo.infrastructure.driver_adapter.bingoBalls.mapper;

import com.bingo.appbingo.domain.model.cardbingo.BingoBalls;
import com.bingo.appbingo.infrastructure.driver_adapter.bingoBalls.BingoBallsEntity;

public class BingoBallsMapper {

    private BingoBallsMapper() {
        throw new IllegalStateException("Utility class");
    }
        public static BingoBalls bingoBallsEntityBingoBalls(BingoBallsEntity bingoBallsEntity){
        return BingoBalls.builder()
                .id(bingoBallsEntity.getId())
                .numbers(bingoBallsEntity.getNumbers())
                .state(bingoBallsEntity.getState())
                .cardBingoId(bingoBallsEntity.getCardBingoId())
                .build();

    }

    public static BingoBallsEntity bingoBallsABingoBallsEntity(BingoBalls bingoBalls){
        return BingoBallsEntity.builder()
                .id(bingoBalls.getId())
                .numbers(bingoBalls.getNumbers())
                .state(bingoBalls.getState())
                .cardBingoId(bingoBalls.getCardBingoId())
                .build();

    }
}
