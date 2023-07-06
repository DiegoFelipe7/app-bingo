package com.bingo.appbingo.domain.model.cardbingo;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
public class BingoBalls {
    private Integer id;
    private String numbers;
    private Boolean state;
    private Integer cardBingoId;

    public BingoBalls(String numbers, Boolean state) {
        this.numbers = numbers;
        this.state = state;
    }
}
