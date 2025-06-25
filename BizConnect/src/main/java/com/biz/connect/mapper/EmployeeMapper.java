package com.biz.connect.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.biz.connect.domain.Employee;

@Mapper
public interface EmployeeMapper {
    @Select("""
        SELECT
            e.e_id AS eId,
            e.e_name AS eName,
            d.d_name AS dName,
            e.e_tel AS eTel
        FROM T_EMPLOYEE e
        LEFT JOIN M_DIVISION d ON e.d_id = d.d_id
        WHERE e.delete_flag = 0
        ORDER BY e.e_id
    """)
    List<Employee> findAllWithDivision();

    @Insert("""
        INSERT INTO T_EMPLOYEE (
            e_id, e_name, e_kana, birthday, d_id, e_tel, e_email, e_created_name
        ) VALUES (
            #{eId}, #{eName}, #{eKana}, #{birthday}, #{dId}, #{eTel}, #{eEmail}, #{eCreatedName}
        )
    """)
    void insert(Employee employee);
    
    @Select("""
        SELECT
            e_id AS eId,
            e_name AS eName,
            e_kana AS eKana,
            birthday,
            d_id AS dId,
            e_tel AS eTel,
            e_email AS eEmail,
            e_created_name AS eCreatedName
        FROM T_EMPLOYEE
        WHERE e_id = #{eId} AND delete_flag = 0
    """)
    Employee findById(String eId);

    List<Employee> findByConditions(
        @Param("eId") String eId,
        @Param("eName") String eName,
        @Param("eKana") String eKana,
        @Param("dId") String dId,
        @Param("dName") String dName
    );

    int update(Employee employee);
    
    @Update("UPDATE T_EMPLOYEE SET delete_flag = 1 WHERE e_id = #{eId}")
    void logicalDelete(@Param("eId") String eId);
}