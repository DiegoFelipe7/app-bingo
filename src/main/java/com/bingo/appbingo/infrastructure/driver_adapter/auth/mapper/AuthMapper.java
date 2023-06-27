package com.bingo.appbingo.infrastructure.driver_adapter.auth.mapper;

import com.bingo.appbingo.domain.model.auth.Users;
import com.bingo.appbingo.infrastructure.driver_adapter.auth.UsersEntity;

public class AuthMapper {
    private AuthMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static Users usersEntityAUsers(UsersEntity usersEntity){
        return Users.builder()
                .id(usersEntity.getId())
                .username(usersEntity.getUsername())
                .email(usersEntity.getEmail())
                .password(usersEntity.getPassword())
                .fullName(usersEntity.getFullName())
                .phone(usersEntity.getPhone())
                .country(usersEntity.getCountry())
                .city(usersEntity.getCity())
                .emailVerified(usersEntity.getEmailVerified())
                .token(usersEntity.getToken())
                .photo(usersEntity.getPhoto())
                .refLink(usersEntity.getRefLink())
                .invitationLink(usersEntity.getInvitationLink())
                .roles(usersEntity.getRoles())
                .parentId(usersEntity.getParentId())
                .status(usersEntity.getStatus())
                .level(usersEntity.getLevel())
                .wallet(usersEntity.getWallet())
                .createdAt(usersEntity.getCreatedAt())
                .updatedAt(usersEntity.getUpdatedAt()).build();
    }

    public static UsersEntity usersAUserEntity(Users users){
        return UsersEntity.builder()
                .id(users.getId())
                .username(users.getUsername())
                .email(users.getEmail())
                .password(users.getPassword())
                .fullName(users.getFullName())
                .phone(users.getPhone())
                .country(users.getCountry())
                .city(users.getCity())
                .emailVerified(users.getEmailVerified())
                .token(users.getToken())
                .photo(users.getPhoto())
                .refLink(users.getRefLink())
                .invitationLink(users.getInvitationLink())
                .roles(users.getRoles())
                .parentId(users.getParentId())
                .status(users.getStatus())
                .level(users.getLevel())
                .wallet(users.getWallet())
                .createdAt(users.getCreatedAt())
                .updatedAt(users.getUpdatedAt()).build();
    }
}
