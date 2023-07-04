package com.bingo.appbingo.infrastructure.driver_adapter.lottery;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
@Table(name = "lottery")
public class LotteryEntity {
    @Id
    private Integer id;
    private LocalDateTime createdAt;
    private LocalDateTime startDate;
    private Integer numberOfRounds;
    private Boolean state;
}
