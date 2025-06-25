package com.biz.connect.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import com.biz.connect.domain.Employee;

import lombok.Data;

// HTMLフォームの入力値を一時的に保持する。
// バリデーションアノテーションで、サーバー側の入力チェックを自動化。
// Employee.javaとは分けて、入力専用のオブジェクトとして使う。
@Data
public class EmployeeForm {
    @NotBlank(message = "社員IDは必須項目です")
    @Pattern(regexp = "^EMP\\d{4}$", message = "社員IDはEMPに続けて4桁の数字で入力してください")
    private String eId;

    @NotBlank(message = "氏名（漢字）は必須項目です")
    private String eName;

    @NotBlank(message = "氏名（かな）は必須項目です")
    private String eKana;

    @NotNull(message = "生年月日は必須項目です")
    private java.time.LocalDate birthday;

    @NotBlank(message = "部署は必須項目です")
    @Pattern(regexp = "^DVI\\d{3}$", message = "部署IDはDVIに続けて4桁の数字で入力してください")
    private String dId;

    @NotBlank(message = "電話番号は必須項目です")
    @Pattern(regexp = "^\\d{2,4}-\\d{2,4}-\\d{3,4}$", message = "電話番号の形式で入力してください（例：01-2345-6789）")
    private String eTel;

    @NotBlank(message = "メールアドレスは必須項目です")
    @Email(message = "メールアドレスの形式で入力してください")
    private String eEmail;
    
    public EmployeeForm() {}

    public EmployeeForm(Employee emp) {
        this.eId = emp.getEId();
        this.eName = emp.getEName();
        this.eKana = emp.getEKana();
        Object bd = emp.getBirthday();
        if (bd instanceof java.time.LocalDate) {
            this.birthday = (java.time.LocalDate) bd;
        } else if (bd instanceof java.sql.Date) {
            this.birthday = ((java.sql.Date) bd).toLocalDate();
        } else if (bd instanceof String) {
            // yyyy/MM/dd → yyyy-MM-dd に変換してからLocalDateに
            String s = ((String) bd).replace("/", "-");
            this.birthday = java.time.LocalDate.parse(s);
        } else {
            this.birthday = null;
        }
        this.dId = emp.getDId();
        this.eTel = emp.getETel();
        this.eEmail = emp.getEEmail();
    }
}
