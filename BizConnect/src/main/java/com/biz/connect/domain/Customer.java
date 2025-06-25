package com.biz.connect.domain;

import lombok.Data;

@Data
public class Customer {
    private String cId;            // 顧客ID
    private String company;        // 会社名
    private String address;        // 所在地
    private String cTel;           // 連絡先
    private String cEmail;         // メールアドレス
    private String person;         // 担当者名
    private String cCreatedName;   // 登録者名
    private String cUpdateName;    // 更新者名
}
