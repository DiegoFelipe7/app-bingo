package com.bingo.appbingo.domain.model.users;

import lombok.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class References {

    private Integer id;
    private String fullName;
    private Integer level;
    private String phone;
    private String userName;
    private LocalDateTime dateRegistered;

    public References(Integer id,String fullName, String phone, String userName, LocalDateTime dateRegistered) {
        this.id=id;
        this.fullName = fullName;
        this.phone = phone;
        this.userName = userName;
        this.dateRegistered = dateRegistered;
    }

    public References(Integer id,String fullName, Integer level, String userName, LocalDateTime dateRegistered) {
        this.id=id;
        this.fullName = fullName;
        this.level= level;
        this.userName = userName;
        this.dateRegistered = dateRegistered;
    }

}
