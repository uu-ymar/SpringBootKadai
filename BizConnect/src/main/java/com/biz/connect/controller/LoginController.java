package com.biz.connect.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.biz.connect.domain.MUser;
import com.biz.connect.mapper.MUserMapper;

@Controller
public class LoginController {

    @Autowired
    private MUserMapper userMapper;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login/login"; // 変更点！
    }

    @PostMapping("/login")
    public String login(@RequestParam String admin_user_name,
                        @RequestParam String password,
                        Model model) {
        MUser user = userMapper.findByAdminId(admin_user_name);
        if (user != null && user.getPassword().equals(sha256(password))) {
        	return "redirect:/menu";
        }
        model.addAttribute("error", "ログインに失敗しました");
        return "login/login";
    }

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
    
    @GetMapping("/menu")
    public String showMenu(Model model) {
        model.addAttribute("title", "menu/menu :: title");
        model.addAttribute("content", "menu/menu :: content");
        return "layout/layout";
    }
}
