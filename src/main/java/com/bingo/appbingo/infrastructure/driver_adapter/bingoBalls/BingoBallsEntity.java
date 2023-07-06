package com.bingo.appbingo.infrastructure.driver_adapter.bingoBalls;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
@Table(name = "bingo_balls")
public class BingoBallsEntity {
    @Id
    private Integer id;
    private String numbers;
    private Boolean state;
    private Integer cardBingoId;
}
