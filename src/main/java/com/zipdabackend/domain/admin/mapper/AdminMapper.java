package com.zipdabackend.domain.admin.mapper;

import com.zipdabackend.domain.admin.entity.Admin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper {
    Admin findbyCode(String adminCode);
}




