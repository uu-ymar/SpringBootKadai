package com.biz.connect.domain;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Employee {
    private String eId;             // 社員ID
    private String eName;           // 氏名（漢字）
    private String eKana;           // 氏名（かな）
    private LocalDate birthday;     // 生年月日
    private String dId;             // 部署ID
    private String dName;           // 部署名
    private String eTel;            // 連絡先
    private String eEmail;          // メールアドレス
    private String eCreatedName;    // 登録者名
    private String eUpdateName;     // 更新者名
}
