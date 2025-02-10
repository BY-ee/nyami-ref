package com.project.user.mapper;

import com.project.auth.dto.SignupRequest;
import com.project.user.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "naverId", ignore = true)
    @Mapping(target = "googleId", ignore = true)
    @Mapping(target = "kakaoId", ignore = true)
    @Mapping(target = "profileImage", ignore = true)
    @Mapping(target = "introduction", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Users toUser(SignupRequest signupRequest);
}
