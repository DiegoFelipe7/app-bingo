package com.bingo.appbingo.domain.model.auth;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
public class Login {
    private String email;
    private String password;
    private String ipAddress;
    private String country;
    private String browser;
}
