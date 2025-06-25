package com.biz.connect.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.biz.connect.domain.Customer;

@Mapper
public interface CustomerMapper {
    @Select("""
        SELECT
            c_id AS cId,
            company,
            address,
            c_tel AS cTel,
            c_email AS cEmail,
            person
        FROM T_CUSTOMER
        WHERE delete_flag = 0
        ORDER BY c_id
    """)
    List<Customer> findAll();

    // 顧客IDで1件取得（@Param付きのみ残す）
    @Select("""
        SELECT
            c_id AS cId,
            company,
            address,
            c_tel AS cTel,
            c_email AS cEmail,
            person,
            c_created_name AS cCreatedName
        FROM T_CUSTOMER
        WHERE c_id = #{cId}
          AND delete_flag = 0
    """)
    Customer findById(@Param("cId") String cId);

    @Insert("""
        INSERT INTO T_CUSTOMER (
            c_id, company, address, c_tel, c_email, person, c_created_name
        ) VALUES (
            #{cId}, #{company}, #{address}, #{cTel}, #{cEmail}, #{person}, #{cCreatedName}
        )
    """)
    void insert(Customer customer);

    @Select("""
        SELECT
            c_id AS cId,
            company,
            address,
            c_tel AS cTel,
            c_email AS cEmail,
            person
        FROM T_CUSTOMER
        WHERE delete_flag = 0
          AND (#{cId} IS NULL OR c_id = #{cId})
          AND (#{company} IS NULL OR company LIKE CONCAT('%', #{company}, '%'))
          AND (#{address} IS NULL OR address LIKE CONCAT('%', #{address}, '%'))
          AND (#{person} IS NULL OR person LIKE CONCAT('%', #{person}, '%'))
        ORDER BY c_id
    """)
    List<Customer> search(
        @Param("cId") String cId,
        @Param("company") String company,
        @Param("address") String address,
        @Param("person") String person
    );
    
    @org.apache.ibatis.annotations.Update("""
    	    UPDATE T_CUSTOMER
    	    SET
    	        company = #{company},
    	        address = #{address},
    	        c_tel = #{cTel},
    	        c_email = #{cEmail},
    	        person = #{person},
    	        c_update_name = #{cUpdateName}
    	    WHERE
    	        c_id = #{cId}
    	        AND delete_flag = 0
    	""")
    	void update(Customer customer);
    
    @org.apache.ibatis.annotations.Update("""
    	    UPDATE T_CUSTOMER
    	    SET delete_flag = 1
    	    WHERE c_id = #{cId}
    	""")
    	void deleteById(String cId);
}