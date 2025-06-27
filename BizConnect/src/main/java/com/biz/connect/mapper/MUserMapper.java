package com.biz.connect.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.biz.connect.domain.MUser;

/**
 * 管理ユーザー（M_USERテーブル）へのDBアクセス用MyBatis Mapperインターフェース。
 * 主に管理者IDによるユーザー情報の取得を行う。
 */
@Mapper
public interface MUserMapper {

    /**
     * 管理者IDをもとにユーザー情報を1件取得する
     * @param adminId 検索対象の管理者ID
     * @return 該当するMUserエンティティ（該当なしの場合はnull）
     */
    @Select("SELECT * FROM M_USER WHERE admin_id = #{adminId}")
    MUser findByAdminId(String adminId);
}
