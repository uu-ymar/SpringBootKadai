package com.biz.connect.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.biz.connect.domain.Employee;

/**
 * 社員情報テーブル（T_EMPLOYEE）へのDBアクセスを行うMyBatis Mapperインターフェース
 * 主に社員の検索・登録・更新・論理削除などの機能を提供する
 */
@Mapper
public interface EmployeeMapper {

    /**
     * s-list.htmlで使用
     * 社員一覧を部署名付きで取得する
     * 社員テーブル（T_EMPLOYEE）と部署マスタ（M_DIVISION）をLEFT JOINし、
     * 論理削除されていない社員全件を取得
     * @return 社員＋部署名のリスト
     */
    @Select("""
        SELECT
            e.e_id AS eId,       // 社員ID
            e.e_name AS eName,   // 社員名
            d.d_name AS dName,   // 部署名（部署テーブルからJOIN）
            e.e_tel AS eTel      // 電話番号
        FROM T_EMPLOYEE e
        LEFT JOIN M_DIVISION d ON e.d_id = d.d_id
        WHERE e.delete_flag = 0
        ORDER BY e.e_id
    """)
    List<Employee> findAllWithDivision();

    /**
     * 社員データの新規登録
     * @param employee 登録する社員エンティティ
     */
    @Insert("""
        INSERT INTO T_EMPLOYEE (
            e_id, e_name, e_kana, birthday, d_id, e_tel, e_email, e_created_name
        ) VALUES (
            #{eId}, #{eName}, #{eKana}, #{birthday}, #{dId}, #{eTel}, #{eEmail}, #{eCreatedName}
        )
    """)
    void insert(Employee employee);

    /**
     * 社員IDで1件取得（論理削除されていないデータのみ）
     * @param eId 社員ID
     * @return 該当する社員情報（なければnull）
     */
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

    /**
     * 複数条件による社員リストの検索（部分一致等はSQL定義側で対応）
     * @param eId 社員ID
     * @param eName 社員名
     * @param eKana 社員名カナ
     * @param dId 部署ID
     * @param dName 部署名
     * @return 条件に一致する社員リスト
     */
    List<Employee> findByConditions(
        @Param("eId") String eId,
        @Param("eName") String eName,
        @Param("eKana") String eKana,
        @Param("dId") String dId,
        @Param("dName") String dName
    );

    /**
     * 社員情報の更新
     * @param employee 更新内容
     * @return 更新件数
     */
    int update(Employee employee);

    /**
     * 社員データの論理削除（delete_flag=1をセット）
     * @param eId 削除対象の社員ID
     */
    @Update("UPDATE T_EMPLOYEE SET delete_flag = 1 WHERE e_id = #{eId}")
    void logicalDelete(@Param("eId") String eId);
}
