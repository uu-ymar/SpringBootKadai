package com.biz.connect.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.biz.connect.domain.Division;

@Mapper
public interface DivisionMapper {
    @Select("""
        SELECT
            d_id AS dId,
            d_name AS dName,
            delete_flag
        FROM M_DIVISION
        WHERE d_id = #{dId} AND delete_flag = 0
    """)
    Division findById(String dId);
}