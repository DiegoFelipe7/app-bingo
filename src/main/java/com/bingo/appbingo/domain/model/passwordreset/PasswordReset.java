package com.bingo.appbingo.domain.model.passwordreset;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
public class PasswordReset {
    private Integer id;
    private String email;
    private String token;
    private LocalDateTime duration;
}
