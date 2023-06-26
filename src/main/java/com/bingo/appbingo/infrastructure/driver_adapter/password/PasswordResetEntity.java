package com.bingo.appbingo.infrastructure.driver_adapter.password;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
@Table(name = "password_reset")
public class PasswordResetEntity {
    @Id
    private Integer id;
    private String email;
    private String token;
    private LocalDateTime duration;


    public PasswordResetEntity(String email, String token, LocalDateTime duration) {
        this.email = email;
        this.token = token;
        this.duration = duration;
    }
}
