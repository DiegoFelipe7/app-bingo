package com.bingo.appbingo.infrastructure.driver_adapter.packagepurchase;

import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "package_purchase")
public class PackagePurchaseEntity {
    private Integer id;
    private String username;
    private Integer userParent;
    private Boolean state;
    private LocalDateTime createdAt;
}
