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

    @Autowired  // Spring Frameworkが提供するアノテーション
    private CustomerMapper customerMapper;  // DBアクセス用のMyBatisマッパーを注入

    // 顧客一覧画面の表示
    @GetMapping("/customer/list")  // GETリクエストの「/customer/list」というURLにアクセスした時に実行
    public String showCustomerList(Model model, HttpSession session) {
        List<Customer> customerList = customerMapper.findAll();  // 顧客全件を取得
        model.addAttribute("customerList", customerList);  // 一覧をcustomerListに格納してビューに渡す
        // ログイン中のユーザー名をセッションから取得してloginUserNameに格納
        model.addAttribute("loginUserName", session.getAttribute("loginUserName"));
        return "customer/c-list";  // c-list.htmlを開く
    }
    
    // 顧客登録画面の表示
    @GetMapping("/customer/regist")
    public String showRegist(Model model, HttpSession session) {
        model.addAttribute("loginUserName", session.getAttribute("loginUserName")); // 表示用のログイン中のユーザー名
        model.addAttribute("loginUserId", session.getAttribute("loginUserId")); // 登録時使用するログイン中のユーザーID
        model.addAttribute("customerForm", new CustomerForm());  // 空のフォームオブジェクトを初期化(バインド構文では必須)
        return "customer/c-regist";// c-regist.htmlを開く
    }

    // 顧客登録処理（POST）
    @PostMapping("/customer/regist")
    public String doRegist(
            @ModelAttribute @Validated CustomerForm form,  // フォームバリデーションを有効にする
            BindingResult bindingResult,
            HttpSession session,
            Model model) { // mode：フォーム画面の再表示や、サーバー→テンプレートへのデータ受け渡しに使う入れ物。
    	// 入力エラーがあるかどうか確認
        if (bindingResult.hasErrors()) {  // hasErrors()：エラーあり「true」、エラーなし「false」
            return "customer/c-regist";  // エラー時は元の画面(c-regist.html)に戻る
        }

        // 顧客IDの重複チェック
        if (customerMapper.findById(form.getCId()) != null) {
            // 第1引数 エラーを付ける対象　第2引数 エラーの種類　第3引数 エラーメッセージ
            bindingResult.rejectValue("cId", "duplicate", form.getCId() + "の顧客IDはすでに登録済みです");
            return "customer/c-regist";  // エラー時は元の画面(c-regist.html)に戻る
        }

        // CustomerForm（画面入力値）→ Customer（DB保存用エンティティ）に値を移し替えています。
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
    		// URLのクエリパラメータ（例：/customer/edit?cId=1001&company=abc）を受け取ります。
    		// required = false なので、未入力（null）でもOK
            @RequestParam(required = false) String cId,
            @RequestParam(required = false) String company,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String person,
            HttpSession session,
            Model model) {
        model.addAttribute("loginUserName", session.getAttribute("loginUserName"));

        // 顧客ID、会社名、住所、担当者のうち、1つでも入力されていれば true。
        // 全て未入力なら false。
        boolean hasAnyCondition =
                (cId != null && !cId.isEmpty()) ||
                (company != null && !company.isEmpty()) ||
                (address != null && !address.isEmpty()) ||
                (person != null && !person.isEmpty());

        // 顧客リストの取得方法の分岐
        List<Customer> customerList;
        // 何か入力されていれば（hasAnyConditionがtrue）
        // customerMapper.search() で部分一致検索を実行。
        // 空文字やnullの場合はnullにして渡す（SQLのWHERE条件で無視される）。
        // 全て未入力（hasAnyConditionがfalse）の場合
        // findAll() で全件取得。
        if (hasAnyCondition) {
            // 条件がある場合、部分一致検索を実行（nullは除外）
            customerList = customerMapper.search(
            	/**
            	 * 「cId != null」
				 * … cIdがnull（=値が未設定）でないかどうか
				 * 「!cId.isEmpty()」
				 * … cIdが空文字列（""）でないかどうか
				 * 「? cId : null」
				 * … もし上記2つが両方とも「true」ならcIdを使う。
				 *    どちらかが「false」ならnullを渡す。
            	 */
                cId != null && !cId.isEmpty() ? cId : null,
                company != null && !company.isEmpty() ? company : null,
                address != null && !address.isEmpty() ? address : null,
                person != null && !person.isEmpty() ? person : null
            );
        } else {
            customerList = customerMapper.findAll();  // 条件なし → 全件取得
        }

        // 画面に顧客リストを渡す
        model.addAttribute("customerList", customerList);
        return "customer/c-edit";  // 画面のテンプレート名を返す
    }
    
    // 更新画面表示（既存データをフォームに表示）
    @GetMapping("/customer/update") //↓URLのクエリパラメータcId（例：/customer/update?cId=1001）を受け取る
    public String showCustomerUpdate(@RequestParam("cId") String cId, HttpSession session, Model model) {
        model.addAttribute("loginUserName", session.getAttribute("loginUserName"));
        // 入力された顧客IDに該当するデータをデータベースから取得。
        Customer customer = customerMapper.findById(cId);
        // 指定したIDの顧客が存在しない場合、編集画面（一覧）へリダイレクト。
        // これにより、存在しないデータの編集を防ぎます。
        if (customer == null) {
            return "redirect:/customer/edit";  // 該当データがなければ編集画面へ
        }

        // Customer → CustomerForm に変換
        // DBから取得したCustomerエンティティを画面入力用のCustomerFormにコピーします。
        // これは、フォームとバインドするためです（Thymeleafなどでth:object="${customerForm}"など）。
        CustomerForm form = new CustomerForm();
        form.setCId(customer.getCId());
        form.setCompany(customer.getCompany());
        form.setAddress(customer.getAddress());
        form.setCTel(customer.getCTel());
        form.setCEmail(customer.getCEmail());
        form.setPerson(customer.getPerson());

        // コピーしたフォームデータをcustomerFormという名前で画面に渡します。
        model.addAttribute("customerForm", form);
        return "customer/c-update";
    }
    
    // 更新処理（POST）
    @PostMapping("/customer/update")
    public String updateCustomer(
            @ModelAttribute("customerForm") @Valid CustomerForm customerForm,  // フォームの入力値をcustomerFormオブジェクトに格納し、バリデーションも実行します。
            BindingResult bindingResult,
            HttpSession session,
            Model model) {
    	// バリデーションエラー時の処理
        if (bindingResult.hasErrors()) {
            return "customer/c-update";  // エラーがある場合は戻る
        }

        String loginUserId = (String) session.getAttribute("loginUserId");

        // 画面から受け取ったcustomerFormの値を、DB登録用のCustomerエンティティに詰め替えます。
        // 更新者ID（CUpdateName）もセットします。
        Customer customer = new Customer();
        customer.setCId(customerForm.getCId());
        customer.setCompany(customerForm.getCompany());
        customer.setAddress(customerForm.getAddress());
        customer.setCTel(customerForm.getCTel());
        customer.setCEmail(customerForm.getCEmail());
        customer.setPerson(customerForm.getPerson());
        customer.setCUpdateName(loginUserId);  // 更新者IDをセット

        // MyBatisのcustomerMapperでDBの顧客データを更新します。
        customerMapper.update(customer);  // DB更新

        return "redirect:/customer/edit?updated=1";  // 編集画面にリダイレクト＋パラメータ付き
    }

    // 論理削除処理
    @PostMapping("/customer/delete")
                              // フォームやリンクから送られた顧客ID（cId）を受け取ります。
    public String deleteCustomer(@RequestParam("cId") String cId) {
    	// MyBatisのcustomerMapper.deleteById(cId)を呼び出しています。
        customerMapper.deleteById(cId);  // 該当IDのレコードを削除（論理削除）
        // 削除が終わったら「顧客編集画面（一覧）」にリダイレクトします。
        // ?deleted=1 のクエリパラメータを付けているので、画面側で「削除しました」などの完了メッセージを表示できます。
        return "redirect:/customer/edit?deleted=1";
    }
}
