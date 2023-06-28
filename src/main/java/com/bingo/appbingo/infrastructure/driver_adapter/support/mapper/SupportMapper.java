package com.bingo.appbingo.infrastructure.driver_adapter.support.mapper;

import com.bingo.appbingo.domain.model.support.Support;
import com.bingo.appbingo.infrastructure.driver_adapter.support.SupportEntity;

public class SupportMapper {

    private SupportMapper() {
        throw new IllegalStateException("Utility class");
    }


    public static Support supportEntityASupport(SupportEntity supportEntity){
        return Support.builder()
                .id(supportEntity.getId())
                .ticket(supportEntity.getTicket())
                .category(supportEntity.getCategory())
                .question(supportEntity.getQuestion())
                .answer(supportEntity.getAnswer())
                .userId(supportEntity.getUserId())
                .urlPhoto(supportEntity.getUrlPhoto())
                .state(supportEntity.getState())
                .createdAt(supportEntity.getCreatedAt())
                .updatedAt(supportEntity.getUpdatedAt())
                .build();
    }

    public static SupportEntity supportASupportEntity(Support Support){
        return SupportEntity.builder()
                .id(Support.getId())
                .ticket(Support.getTicket())
                .category(Support.getCategory())
                .question(Support.getQuestion())
                .answer(Support.getAnswer())
                .userId(Support.getUserId())
                .urlPhoto(Support.getUrlPhoto())
                .state(Support.getState())
                .createdAt(Support.getCreatedAt())
                .updatedAt(Support.getUpdatedAt())
                .build();
    }

}
