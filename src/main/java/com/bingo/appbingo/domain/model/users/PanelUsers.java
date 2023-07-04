package com.bingo.appbingo.domain.model.users;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class PanelUsers {
    private String link;
    private BigDecimal balance;
    private BigDecimal awards;
    private Integer reference;


}
