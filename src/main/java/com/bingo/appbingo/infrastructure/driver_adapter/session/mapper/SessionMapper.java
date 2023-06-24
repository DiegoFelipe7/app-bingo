package com.bingo.appbingo.infrastructure.driver_adapter.session.mapper;

import com.bingo.appbingo.domain.model.session.Session;
import com.bingo.appbingo.infrastructure.driver_adapter.session.SessionEntity;

public class SessionMapper {

    private SessionMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static Session sessionEntityASession(SessionEntity sessionEntity) {
        return Session.builder()
                .id(sessionEntity.getId())
                .userId(sessionEntity.getUserId())
                .ipAddress(sessionEntity.getIpAddress())
                .country(sessionEntity.getCountry())
                .browser(sessionEntity.getBrowser())
                .dateOfEntry(sessionEntity.getDateOfEntry())
                .build();
    }

    public static SessionEntity sessionASessionEntity(Session session) {
        return SessionEntity.builder()
                .id(session.getId())
                .userId(session.getUserId())
                .ipAddress(session.getIpAddress())
                .country(session.getCountry())
                .browser(session.getBrowser())
                .dateOfEntry(session.getDateOfEntry())
                .build();
    }


}
