package com.bingo.appbingo.infrastructure.driver_adapter.balls;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@Table(name = "balls")
@ToString
public class BallsEntity {
    @Id
    private Integer id;
    private String ball;
}
