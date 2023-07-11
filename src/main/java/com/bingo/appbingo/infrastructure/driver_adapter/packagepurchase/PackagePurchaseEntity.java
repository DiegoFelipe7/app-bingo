package com.bingo.appbingo.infrastructure.driver_adapter.packagepurchase;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "package_purchase")
public class PackagePurchaseEntity {
    @Id
    private Integer id;
    private Integer userId;
    private String username;
    private Integer userParent;
    private Boolean state;
    private LocalDateTime createdAt;


    public PackagePurchaseEntity(Integer userId, String username, Integer userParent) {
        this.userId = userId;
        this.username = username;
        this.userParent = userParent;

    }
}
