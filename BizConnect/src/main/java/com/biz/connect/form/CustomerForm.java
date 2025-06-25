package com.biz.connect.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Data;

// HTMLフォームの入力値を一時的に保持する。
// バリデーションアノテーションで、サーバー側の入力チェックを自動化。
// Customer.javaとは分けて、入力専用のオブジェクトとして使う。
@Data
public class CustomerForm {
	@NotBlank(message = "顧客IDは必須項目です")
	@Pattern(regexp = "^CUS\\d{4}$", message = "顧客IDはCUSに続けて4桁の数字で入力してください")
	private String cId;

    @NotBlank(message = "会社名は必須項目です")
    @Size(max = 255, message = "会社名は255文字以内で入力してください")
    private String company;

    @NotBlank(message = "所在地は必須項目です")
    private String address;

    @NotBlank(message = "電話番号は必須項目です")
    @Pattern(regexp = "\\d{2,4}-\\d{2,4}-\\d{3,4}", message = "電話番号の形式が正しくありません")
    private String cTel;

    @NotBlank(message = "メールアドレスは必須項目です")
    @Email(message = "メールアドレスの形式が正しくありません")
    private String cEmail;

    @NotBlank(message = "担当者は必須項目です")
    private String person;
}
