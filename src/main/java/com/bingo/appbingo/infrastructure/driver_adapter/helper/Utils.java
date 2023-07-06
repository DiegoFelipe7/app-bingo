package com.bingo.appbingo.infrastructure.driver_adapter.helper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class Utils {
    public static String extractUsername(String refLink) {
        int position = refLink.lastIndexOf('/');
        return refLink.substring(position + 1);
    }

    public static String uid() {
        return UUID.randomUUID().toString();
    }

    public static Double bonus(Integer level) {
        Map<Integer, Double> bonusMap = Map.of(
                1, 0.4,
                2, 0.3,
                3, 0.2,
                4, 0.1
        );
        return bonusMap.getOrDefault(level, 0.0);
    }

    public static LocalDateTime starDate(String starDate){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm", Locale.US);
        return LocalDateTime.parse(starDate,dateFormatter);
    }

    public static String formatStartDate(LocalDateTime startDate) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        return startDate.format(dateFormatter);
    }




}
