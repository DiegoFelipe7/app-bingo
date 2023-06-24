package com.bingo.appbingo.domain.model.auth;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
public class Token {
    private String token;
}
