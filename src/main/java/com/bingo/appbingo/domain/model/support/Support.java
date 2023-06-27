package com.bingo.appbingo.domain.model.support;
import com.bingo.appbingo.domain.model.enums.StateSupport;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Support {
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

    public Support(Integer id, String ticket, String category, String question, String answer, String urlPhoto, StateSupport state, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.ticket = ticket;
        this.category = category;
        this.question = question;
        this.answer = answer;
        this.urlPhoto = urlPhoto;
        this.state = state;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
