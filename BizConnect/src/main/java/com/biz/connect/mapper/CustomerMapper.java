package com.biz.connect.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.biz.connect.domain.Customer;

/**
 * 顧客テーブル(T_CUSTOMER)へのDBアクセスを行うMyBatis Mapperインターフェース
 * 主にCRUD（検索・登録・更新・論理削除）と条件検索を提供
 */
@Mapper
public interface CustomerMapper {

    /**
     * 顧客一覧を取得（論理削除されていない全件）
     * @return 顧客リスト
     */
	
	// AS：SQLの結果セットの列名がJavaのフィールド名と一致し、正しくデータがセットされる
	// MapperではcId、DBではc_id
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

    /**
     * 顧客IDで1件取得（論理削除されていないデータのみ）
     * @param cId 顧客ID
     * @return 該当顧客データ（なければnull）
     */
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

    /**
     * 顧客データの新規登録
     * @param customer 登録する顧客エンティティ
     */
    @Insert("""
        INSERT INTO T_CUSTOMER (
            c_id, company, address, c_tel, c_email, person, c_created_name
        ) VALUES (
            #{cId}, #{company}, #{address}, #{cTel}, #{cEmail}, #{person}, #{cCreatedName}
        )
    """)
    void insert(Customer customer);

    /**
     * 検索条件付き顧客一覧取得（論理削除されていないデータのみ）
     * 各条件は部分一致（company/address/person）または完全一致（cId）。条件未指定時は全件。
     * @param cId 顧客ID
     * @param company 会社名
     * @param address 住所
     * @param person 担当者名
     * @return 条件に合致する顧客リスト
     */
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
    
    /**
     * 顧客データの更新（論理削除されていないデータのみ更新）
     * @param customer 更新する顧客エンティティ
     */
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
    
    /**
     * 顧客データの論理削除（delete_flag=1をセット）
     * @param cId 削除対象の顧客ID
     */
    @org.apache.ibatis.annotations.Update("""
        UPDATE T_CUSTOMER
        SET delete_flag = 1
        WHERE c_id = #{cId}
    """)
    void deleteById(String cId);
}
