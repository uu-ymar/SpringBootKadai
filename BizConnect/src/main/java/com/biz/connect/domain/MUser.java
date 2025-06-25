package com.biz.connect.domain;

import lombok.Data;

@Data
public class MUser {
    private String adminId;
    private String adminName;
    private String password;
    private java.sql.Date createDay;
}
