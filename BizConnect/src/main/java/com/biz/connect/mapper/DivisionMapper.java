package com.biz.connect.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.biz.connect.domain.Division;

/**
 * 部署（Division）テーブルへのDBアクセス用MyBatis Mapperインターフェース。
 * 主に部署IDによる部署データ取得を行う。
 */
@Mapper
public interface DivisionMapper {

    /**
     * 部署IDで1件取得（論理削除されていないデータのみ取得）
     * @param dId 取得したい部署ID
     * @return 該当するDivisionエンティティ。存在しなければnull。
     */
    @Select("""
        SELECT
            d_id AS dId,          // DBカラム名をJavaプロパティ名にマッピング
            d_name AS dName,
            delete_flag
        FROM M_DIVISION
        WHERE d_id = #{dId}      // 指定された部署IDで検索
          AND delete_flag = 0    // 論理削除されていないデータのみ対象
    """)
    Division findById(String dId);
}
