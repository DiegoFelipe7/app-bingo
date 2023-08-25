package com.bingo.appbingo.infrastructure.driver_adapter.lottery.mapper;

import com.bingo.appbingo.domain.model.lottery.Lottery;
import com.bingo.appbingo.domain.model.lottery.LotteryDto;
import com.bingo.appbingo.domain.model.round.Round;
import com.bingo.appbingo.infrastructure.driver_adapter.helper.Utils;
import com.bingo.appbingo.infrastructure.driver_adapter.lottery.LotteryEntity;

import java.util.List;

public class LotteryMapper {

    private LotteryMapper() {
        throw new IllegalStateException("Utility class");
    }
    public static Lottery lotteryEntityALottery(LotteryEntity lotteryEntity){
        return Lottery.builder()
                .id(lotteryEntity.getId())
                .key(lotteryEntity.getKey())
                .createdAt(lotteryEntity.getCreatedAt())
                .startDate(lotteryEntity.getStartDate())
                .numberOfRounds(lotteryEntity.getNumberOfRounds())
                .price(lotteryEntity.getPrice())
                .state(lotteryEntity.getState())
                .build();
    }
    public static LotteryEntity lotteryALotteryEntity(Lottery lottery){
        return LotteryEntity.builder()
                .id(lottery.getId())
                .key(Utils.uid())
                .createdAt(lottery.getCreatedAt())
                .startDate(lottery.getStartDate())
                .numberOfRounds(lottery.getNumberOfRounds())
                .price(lottery.getPrice())
                .state(lottery.getState())
                .build();
    }

    public static LotteryEntity lotteryDtoALotteryEntity(LotteryDto lottery){
        return LotteryEntity.builder()
                .id(lottery.getId())
                .key(lottery.getKey())
                .createdAt(lottery.getCreatedAt())
                .startDate(Utils.starDate(lottery.getStartDate()))
                .numberOfRounds(lottery.getNumberOfRounds())
                .state(lottery.getState())
                .build();
    }

    public static LotteryDto lotteryDto(LotteryEntity lotteryEntity, List<Round> round){
        return LotteryDto.builder()
                .id(lotteryEntity.getId())
                .key(lotteryEntity.getKey())
                .createdAt(lotteryEntity.getCreatedAt())
                .startDate(Utils.formatStartDate(lotteryEntity.getStartDate()))
                .numberOfRounds(lotteryEntity.getNumberOfRounds())
                .rounds(round)
                .price(lotteryEntity.getPrice())
                .state(lotteryEntity.getState())
                .build();
    }

}
