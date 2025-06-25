package com.biz.connect.domain;

import lombok.Data;

@Data
public class MUser {
    private String adminId;          // ログインID
    private String adminName;        // 表示氏名
    private String password;         // パスワード
    private java.sql.Date createDay; // 作成日
}
