package com.bingo.appbingo.domain.model.cardbingo;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
public class BingoCard {
    private Integer numbers;
    private Boolean state;

}
