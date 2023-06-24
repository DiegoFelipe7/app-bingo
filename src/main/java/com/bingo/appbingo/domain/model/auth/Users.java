package com.bingo.appbingo.domain.model.auth;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
public class Users {
    private Integer id;
    private String username;
    private String email;
    private String password;
    private String fullName;
    private String phone;
    private String country;
    private String city;
    private LocalDateTime emailVerified;
    private String token;
    private String photo;
    private String refLink;
    private String invitationLink;
    private String  roles;
    private Integer parentId;
    private Boolean status;
    private Integer level;
    private String wallet;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
