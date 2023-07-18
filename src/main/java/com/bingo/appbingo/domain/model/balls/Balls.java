package com.bingo.appbingo.domain.model.balls;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ToString
public class Balls {
    private Integer id;
    private String ball;
}
