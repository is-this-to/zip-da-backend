package com.zipdabackend.domain.user.mapper;

import com.zipdabackend.domain.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User findByEmail(String email);
    User findByNick(String nick);
    User findByPk(long userId);
}