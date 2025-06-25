package com.biz.connect.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.biz.connect.domain.MUser;

@Mapper
public interface MUserMapper {

    @Select("SELECT * FROM M_USER WHERE admin_id = #{adminId}")
    MUser findByAdminId(String adminId);
}
