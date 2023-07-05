package com.bingo.appbingo.domain.model.cardbingo;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
public class BingoCard {
    private String id;
    private Map<Character,Integer> numbers;
}
