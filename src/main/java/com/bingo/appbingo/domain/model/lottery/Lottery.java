package com.bingo.appbingo.domain.model.lottery;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
public class Lottery {
    private Integer id;
    private String key;
    private LocalDateTime createdAt;
    private LocalDateTime startDate;
    private Integer numberOfRounds;
    private Boolean state;
}
