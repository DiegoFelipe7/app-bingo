package com.bingo.appbingo.domain.model.support;

import com.bingo.appbingo.domain.model.enums.StateSupport;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor

public class SupportDto extends Support {
    private String username;
    private String email;


    public SupportDto(Integer id,  String ticket, String category, String question, String answer, String username ,String email,String urlPhoto, StateSupport state, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, ticket, category, question, answer, urlPhoto, state, createdAt, updatedAt);
        this.username = username;
        this.email = email;
    }

}
