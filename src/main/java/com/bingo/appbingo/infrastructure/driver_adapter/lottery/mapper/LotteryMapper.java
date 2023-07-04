package com.bingo.appbingo.infrastructure.driver_adapter.lottery.mapper;

import com.bingo.appbingo.domain.model.history.PaymentHistory;
import com.bingo.appbingo.domain.model.lottery.Lottery;
import com.bingo.appbingo.infrastructure.driver_adapter.history.PaymentHistoryEntity;
import com.bingo.appbingo.infrastructure.driver_adapter.lottery.LotteryEntity;

public class LotteryMapper {

    private LotteryMapper() {
        throw new IllegalStateException("Utility class");
    }
    public static Lottery lotteryEntityALottery(LotteryEntity lotteryEntity){
        return Lottery.builder()
                .id(lotteryEntity.getId())
                .createdAt(lotteryEntity.getCreatedAt())
                .startDate(lotteryEntity.getStartDate())
                .numberOfRounds(lotteryEntity.getNumberOfRounds())
                .state(lotteryEntity.getState())
                .build();
    }
    public static LotteryEntity lotteryALotteryEntity(Lottery lottery){
        return LotteryEntity.builder()
                .id(lottery.getId())
                .createdAt(lottery.getCreatedAt())
                .startDate(lottery.getStartDate())
                .numberOfRounds(lottery.getNumberOfRounds())
                .state(lottery.getState())
                .build();
    }

}
