package com.bingo.appbingo.infrastructure.driver_adapter.balls.mapper;

import com.bingo.appbingo.domain.model.balls.Balls;
import com.bingo.appbingo.infrastructure.driver_adapter.balls.BallsEntity;

public class BallsMapper {
    private BallsMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static Balls ballsEntityABalls(BallsEntity ballsEntity){
        return Balls.builder()
                .id(ballsEntity.getId())
                .ball(ballsEntity.getBall())
                .build();
    }

    public static BallsEntity ballsABallsEntity(Balls balls){
        return BallsEntity.builder()
                .id(balls.getId())
                .ball(balls.getBall())
                .build();
    }
}
