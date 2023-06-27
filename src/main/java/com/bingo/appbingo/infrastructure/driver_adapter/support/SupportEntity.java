package com.bingo.appbingo.infrastructure.driver_adapter.support;

import com.bingo.appbingo.domain.model.enums.StateSupport;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "support")
public class SupportEntity {
    @Id
    private Integer id;
    private String ticket;
    private String category;
    private String question;
    private String answer;
    private Integer userId;
    private String urlPhoto;
    private StateSupport state;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

