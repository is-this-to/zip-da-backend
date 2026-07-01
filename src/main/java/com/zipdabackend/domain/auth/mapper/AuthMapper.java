package com.zipdabackend.domain.auth.mapper;

import com.zipdabackend.domain.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthMapper {
    int insertUser(User user);
    int updateRefreshToken(long userId, String refreshToken);
    int updateAdminRefreshToken(long adminId, String refreshToken);
}
