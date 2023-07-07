package com.bingo.appbingo.infrastructure.driver_adapter.users.mapper;

import com.bingo.appbingo.domain.model.auth.Users;
import com.bingo.appbingo.domain.model.users.References;
import com.bingo.appbingo.infrastructure.driver_adapter.auth.UsersEntity;
import org.springframework.security.core.userdetails.User;

public class UserMapper {
    private UserMapper() {
        throw new IllegalStateException("Utility class");
    }


    public static Users usersEntityAUsers(UsersEntity usersEntity) {
        return Users.builder()
                .id(usersEntity.getId())
                .username(usersEntity.getUsername())
                .email(usersEntity.getEmail())
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
                .createdAt(usersEntity.getCreatedAt())
                .updatedAt(usersEntity.getUpdatedAt()).build();
    }

    public static UsersEntity usersAUserEntity(Users users) {
        return UsersEntity.builder()
                .id(users.getId())
                .username(users.getUsername())
                .email(users.getEmail())
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
                .createdAt(users.getCreatedAt())
                .updatedAt(users.getUpdatedAt()).build();
    }

    public static References referencesDirect(UsersEntity users,Integer id) {
        return References.builder()
                .id(id)
                .fullName(users.getFullName())
                .phone(users.getPhone())
                .userName(users.getFullName())
                .dateRegistered(users.getCreatedAt())
                .build();
    }

    public static References referencesLevel(UsersEntity users,Integer id) {
        return References.builder()
                .id(id)
                .fullName(users.getFullName())
                .level(users.getLevel())
                .userName(users.getFullName())
                .dateRegistered(users.getCreatedAt())
                .build();
    }
}
