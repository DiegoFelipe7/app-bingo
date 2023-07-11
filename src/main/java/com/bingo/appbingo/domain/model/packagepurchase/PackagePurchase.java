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
    private Integer userId;
    private String username;
    private Integer userParent;
    private Boolean state;
    private LocalDateTime createdAt;

    public PackagePurchase(Integer userId, String username, Integer userParent, Boolean state, LocalDateTime createdAt) {
        this.userId = userId;
        this.username = username;
        this.userParent = userParent;
        this.state = state;
        this.createdAt = createdAt;
    }
}
