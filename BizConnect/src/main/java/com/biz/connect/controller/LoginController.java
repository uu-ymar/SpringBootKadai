package com.biz.connect.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.biz.connect.domain.MUser;
import com.biz.connect.mapper.MUserMapper;

@Controller  // Spring MVCのコントローラー
public class LoginController {

    @Autowired
    private MUserMapper userMapper;  // ログインユーザーを検索するためのMyBatis Mapper

    @Autowired
    private HttpSession session;  // セッション管理のために注入（ユーザー情報の保存に使用）

    // ログイン画面を表示（GETリクエスト時）
    @GetMapping("/login")
    public String showLoginForm() {
        return "login/login";  // login/login.htmlを返す
    }

    // ログイン処理（POSTリクエスト時）
    @PostMapping("/login")
    public String login(
            @RequestParam String admin_user_name,  // ユーザーID（ログインID）
            @RequestParam String password,         // 入力されたパスワード（生）
            Model model) {

        // ユーザーIDでDBからユーザーを検索
        MUser user = userMapper.findByAdminId(admin_user_name);

        // ユーザーが存在し、かつパスワード（ハッシュ）が一致する場合
        if (user != null && user.getPassword().equals(sha256(password))) {
            // ログイン成功 → セッションにユーザー名とIDを保存
            session.setAttribute("loginUserName", user.getAdminName());
            session.setAttribute("loginUserId", user.getAdminId());

            return "redirect:/menu";  // メニュー画面へリダイレクト
        }

        // ログイン失敗時：エラーメッセージを設定して再表示
        model.addAttribute("error", "ログインに失敗しました");
        return "login/login";
    }

    // パスワードをSHA-256でハッシュ化するメソッド(AI作成)
    private String sha256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash)
                hexString.append(String.format("%02x", b));
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // メニュー画面表示
    @GetMapping("/menu")
    public String showMenu(Model model, HttpSession session) {
        Object loginUserName = session.getAttribute("loginUserName");  // セッションからユーザー名取得
        if (loginUserName != null) {
            model.addAttribute("loginUserName", loginUserName);  // 表示用に渡す
        }
        return "menu/menu";  // メニュー画面
    }
}
