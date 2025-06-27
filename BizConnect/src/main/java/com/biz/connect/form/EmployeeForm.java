package com.biz.connect.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import com.biz.connect.domain.Employee;

import lombok.Data;

/**
 * 社員情報入力フォームの値を一時的に保持するクラス
 * - HTMLフォームの入力値をサーバー側で受け取るためのDTO（データ転送オブジェクト）
 * - バリデーションアノテーションで入力チェックを自動化
 * - エンティティ（Employee.java）とは分離し、入力専用として利用
 */
@Data
public class EmployeeForm {

	// 社員ID（例：EMP0001）。必須・形式チェックあり
    @NotBlank(message = "社員IDは必須項目です")
    @Pattern(regexp = "^EMP\\d{4}$", message = "社員IDはEMPに続けて4桁の数字で入力してください")
    private String eId;

    // 氏名（漢字）。必須
    @NotBlank(message = "氏名（漢字）は必須項目です")
    private String eName;

    // 氏名（かな）。必須
    @NotBlank(message = "氏名（かな）は必須項目です")
    private String eKana;

    // 生年月日。必須（LocalDate型)
    @NotNull(message = "生年月日は必須項目です")
    private java.time.LocalDate birthday;

    // 部署ID（例：DVI0001）。必須・形式チェックあり
    @NotBlank(message = "部署は必須項目です")
    @Pattern(regexp = "^DVI\\d{3}$", message = "部署IDはDVIに続けて4桁の数字で入力してください")
    private String dId;

    // 電話番号。必須・「000-0000-0000」などの形式を要求
    @NotBlank(message = "電話番号は必須項目です")
    @Pattern(regexp = "^\\d{2,4}-\\d{2,4}-\\d{3,4}$", message = "電話番号の形式で入力してください（例：01-2345-6789）")
    private String eTel;

    @NotBlank(message = "メールアドレスは必須項目です")
    @Email(message = "メールアドレスの形式で入力してください")
    private String eEmail;
    
    /**
     * デフォルトコンストラクタ
     * - 新規登録時や空フォーム表示時に利用
     */
    public EmployeeForm() {}

    /**
     * AIが記入
     * Employeeエンティティからフォーム値へ変換するコンストラクタ
     * - 編集時、エンティティのデータをHTMLフォームに詰め直す用途で使用
     * - 生年月日は型変換（LocalDate, java.sql.Date, Stringいずれも対応）
     * @param emp 変換元のEmployeeインスタンス
     */
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
