package com.bingo.appbingo.domain.model.packagepurchase;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class PackagePurchase {
    private Integer id;
    private String username;
    private Integer userParent;
    private Boolean state;
    private LocalDateTime createdAt;
}
