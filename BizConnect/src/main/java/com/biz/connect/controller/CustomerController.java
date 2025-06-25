package com.biz.connect.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.biz.connect.domain.Customer;
import com.biz.connect.form.CustomerForm;
import com.biz.connect.mapper.CustomerMapper;

@Controller  // Spring MVC のコントローラーであることを示す
public class CustomerController {

    @Autowired
    private CustomerMapper customerMapper;  // DBアクセス用のMyBatisマッパーを注入

    // 顧客一覧画面の表示
    @GetMapping("/customer/list")
    public String showCustomerList(Model model, HttpSession session) {
        List<Customer> customerList = customerMapper.findAll();  // 顧客全件を取得
        model.addAttribute("customerList", customerList);  // 一覧をビューに渡す
        model.addAttribute("loginUserName", session.getAttribute("loginUserName"));  // ログイン中のユーザー名
        return "customer/c-list";  // Thymeleafテンプレート名（HTMLファイル）
    }
    
    // 顧客登録画面の表示
    @GetMapping("/customer/regist")
    public String showRegist(Model model, HttpSession session) {
        model.addAttribute("loginUserName", session.getAttribute("loginUserName"));
        model.addAttribute("loginUserId", session.getAttribute("loginUserId"));
        model.addAttribute("customerForm", new CustomerForm());  // 空のフォームオブジェクトを初期化
        return "customer/c-regist";
    }

    // 顧客登録処理（POST）
    @PostMapping("/customer/regist")
    public String doRegist(
            @ModelAttribute @Validated CustomerForm form,  // フォームバリデーションを有効にする
            BindingResult bindingResult,
            HttpSession session,
            Model model) {
        if (bindingResult.hasErrors()) {  // 入力エラーがあるかどうかを確認
            return "customer/c-regist";  // エラー時は元の画面に戻る
        }

        // 顧客IDの重複チェック
        if (customerMapper.findById(form.getCId()) != null) {
            bindingResult.rejectValue("cId", "duplicate", form.getCId() + "の顧客IDはすでに登録済みです");
            return "customer/c-regist";
        }

        // CustomerForm → Customer へ変換
        Customer customer = new Customer();
        customer.setCId(form.getCId());
        customer.setCompany(form.getCompany());
        customer.setAddress(form.getAddress());
        customer.setCTel(form.getCTel());
        customer.setCEmail(form.getCEmail());
        customer.setPerson(form.getPerson());
        customer.setCCreatedName((String) session.getAttribute("loginUserId"));  // 登録者のIDをセット

        customerMapper.insert(customer);  // DBに登録

        return "redirect:/customer/list";  // 一覧画面にリダイレクト
    }
    
    // 顧客編集画面表示（検索付き一覧）
    @GetMapping("/customer/edit")
    public String showCustomerEdit(
            @RequestParam(required = false) String cId,
            @RequestParam(required = false) String company,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String person,
            HttpSession session,
            Model model) {
        model.addAttribute("loginUserName", session.getAttribute("loginUserName"));

        // 検索条件のどれかが入力されているか
        boolean hasAnyCondition =
                (cId != null && !cId.isEmpty()) ||
                (company != null && !company.isEmpty()) ||
                (address != null && !address.isEmpty()) ||
                (person != null && !person.isEmpty());

        List<Customer> customerList;
        if (hasAnyCondition) {
            // 条件がある場合、部分一致検索を実行（nullは除外）
            customerList = customerMapper.search(
                cId != null && !cId.isEmpty() ? cId : null,
                company != null && !company.isEmpty() ? company : null,
                address != null && !address.isEmpty() ? address : null,
                person != null && !person.isEmpty() ? person : null
            );
        } else {
            customerList = customerMapper.findAll();  // 条件なし → 全件取得
        }

        model.addAttribute("customerList", customerList);
        return "customer/c-edit";
    }
    
    // 更新画面表示（既存データをフォームに表示）
    @GetMapping("/customer/update")
    public String showCustomerUpdate(@RequestParam("cId") String cId, HttpSession session, Model model) {
        model.addAttribute("loginUserName", session.getAttribute("loginUserName"));
        Customer customer = customerMapper.findById(cId);
        if (customer == null) {
            return "redirect:/customer/edit";  // 該当データがなければ編集画面へ
        }

        // Customer → CustomerForm に変換
        CustomerForm form = new CustomerForm();
        form.setCId(customer.getCId());
        form.setCompany(customer.getCompany());
        form.setAddress(customer.getAddress());
        form.setCTel(customer.getCTel());
        form.setCEmail(customer.getCEmail());
        form.setPerson(customer.getPerson());

        model.addAttribute("customerForm", form);
        return "customer/c-update";
    }
    
    // 更新処理（POST）
    @PostMapping("/customer/update")
    public String updateCustomer(
            @ModelAttribute("customerForm") @Valid CustomerForm customerForm,
            BindingResult bindingResult,
            HttpSession session,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "customer/c-update";  // エラーがある場合は戻る
        }

        String loginUserId = (String) session.getAttribute("loginUserId");

        // 入力値をエンティティに変換
        Customer customer = new Customer();
        customer.setCId(customerForm.getCId());
        customer.setCompany(customerForm.getCompany());
        customer.setAddress(customerForm.getAddress());
        customer.setCTel(customerForm.getCTel());
        customer.setCEmail(customerForm.getCEmail());
        customer.setPerson(customerForm.getPerson());
        customer.setCUpdateName(loginUserId);  // 更新者IDをセット

        customerMapper.update(customer);  // DB更新

        return "redirect:/customer/edit?updated=1";  // 編集画面にリダイレクト＋パラメータ付き
    }

    // 論理削除処理
    @PostMapping("/customer/delete")
    public String deleteCustomer(@RequestParam("cId") String cId) {
        customerMapper.deleteById(cId);  // 該当IDのレコードを削除（論理削除）
        return "redirect:/customer/edit?deleted=1";
    }
}
