package com.bingo.appbingo.domain.model.session;

import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Session {
    private Integer id;
    private Integer userId;
    private String ipAddress;
    private String country;
    private String browser;
    private LocalDateTime dateOfEntry;
}
