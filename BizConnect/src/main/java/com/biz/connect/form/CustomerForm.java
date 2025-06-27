package com.biz.connect.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Data;

/**
 * 顧客情報入力フォームの値を一時的に保持するクラス
 * - HTMLフォームの入力値をサーバー側で受け取るためのDTO（データ転送オブジェクト）
 * - バリデーションアノテーションで入力チェックを自動化
 * - エンティティ（Customer.java）とは分離し、入力専用として利用
 */
@Data
public class CustomerForm {
    
    // 顧客ID（例：CUS0001など）。必須・形式チェックあり
    @NotBlank(message = "顧客IDは必須項目です")
    @Pattern(regexp = "^CUS\\d{4}$", message = "顧客IDはCUSに続けて4桁の数字で入力してください")
    private String cId;

    // 会社名。必須・最大255文字
    @NotBlank(message = "会社名は必須項目です")
    @Size(max = 255, message = "会社名は255文字以内で入力してください")
    private String company;

    // 所在地。必須
    @NotBlank(message = "所在地は必須項目です")
    private String address;

    // 電話番号。必須・「000-0000-0000」などの形式を要求
    @NotBlank(message = "電話番号は必須項目です")
    @Pattern(regexp = "\\d{2,4}-\\d{2,4}-\\d{3,4}", message = "電話番号の形式が正しくありません")
    private String cTel;

    // メールアドレス。必須・メール形式バリデーション
    @NotBlank(message = "メールアドレスは必須項目です")
    @Email(message = "メールアドレスの形式が正しくありません")
    private String cEmail;

    // 担当者名。必須
    @NotBlank(message = "担当者は必須項目です")
    private String person;
}
