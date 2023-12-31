package com.bingo.appbingo.domain.model.packagepurchase;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ToString
public class PackagePurchase {
    private Integer id;
    private Integer userId;
    private String username;
    private Integer userParent;
    private Boolean state;
    private LocalDateTime createdAt;


}
