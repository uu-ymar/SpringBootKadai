package com.biz.connect.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.biz.connect.domain.Employee;
import com.biz.connect.form.EmployeeForm;
import com.biz.connect.mapper.DivisionMapper;
import com.biz.connect.mapper.EmployeeMapper;

@Controller  // このクラスがWebコントローラであることを示す
public class EmployeeController {

    @Autowired
    private EmployeeMapper employeeMapper;  // 社員データへのDB操作を行うMyBatisのインタフェース

    @Autowired
    private DivisionMapper divisionMapper;  // 部署データへのDB操作用（登録・更新時に部署IDの妥当性確認で使用）

    // 社員一覧表示
    @GetMapping("/employee/list")
    public String showEmployeeList(Model model, HttpSession session) {
        List<Employee> employeeList = employeeMapper.findAllWithDivision();  // 社員＋部署情報をJOINして取得
        model.addAttribute("employeeList", employeeList);
        model.addAttribute("loginUserName", session.getAttribute("loginUserName"));  // ログインユーザー表示用
        model.addAttribute("loginUserId", session.getAttribute("loginUserId"));  // ログイン中のID
        return "employee/s-list";  // 一覧画面（Thymeleafテンプレート）
    }

    // 社員登録画面の表示
    @GetMapping("/employee/regist")
    public String showRegist(Model model, HttpSession session) {
        model.addAttribute("employeeForm", new EmployeeForm());  // 空のフォームオブジェクト
        model.addAttribute("loginUserName", session.getAttribute("loginUserName"));
        model.addAttribute("loginUserId", session.getAttribute("loginUserId"));
        return "employee/s-regist";
    }

    // 社員登録処理
    @PostMapping("/employee/regist")
    public String doRegist(
            @ModelAttribute("employeeForm") @Validated EmployeeForm form,  // 入力値をバリデーション付きで取得
            BindingResult bindingResult,
            HttpSession session,
            Model model) {

        // ①社員IDの重複チェック
        if (employeeMapper.findById(form.getEId()) != null) {
            bindingResult.rejectValue("eId", "duplicate", form.getEId() + "の社員IDはすでに登録済みです");
        }

        // ②入力された部署IDが存在するかチェック（DBにあるか）
        if (form.getDId() != null && !form.getDId().isBlank()) {
            if (divisionMapper.findById(form.getDId()) == null) {
                bindingResult.rejectValue("dId", "notfound", "存在しない部署IDです");
            }
        }

        // 入力エラーがあれば元の画面へ戻す
        if (bindingResult.hasErrors()) {
            model.addAttribute("loginUserName", session.getAttribute("loginUserName"));
            model.addAttribute("loginUserId", session.getAttribute("loginUserId"));
            return "employee/s-regist";
        }

        // 入力内容をエンティティへ変換して登録
        Employee emp = new Employee();
        emp.setEId(form.getEId());
        emp.setEName(form.getEName());
        emp.setEKana(form.getEKana());
        emp.setBirthday(form.getBirthday());
        emp.setDId(form.getDId());
        emp.setETel(form.getETel());
        emp.setEEmail(form.getEEmail());
        emp.setECreatedName((String) session.getAttribute("loginUserId"));  // 登録者ID

        employeeMapper.insert(emp);  // DBへ登録実行

        return "redirect:/employee/list";  // 一覧画面へリダイレクト
    }

    // 社員編集画面（検索付き一覧）
    @GetMapping("/employee/edit")
    public String editEmployeeList(
        @RequestParam(name = "eId", required = false) String eId,
        @RequestParam(name = "eName", required = false) String eName,
        @RequestParam(name = "eKana", required = false) String eKana,
        @RequestParam(name = "dId", required = false) String dId,
        @RequestParam(name = "dName", required = false) String dName,
        HttpSession session, Model model
    ) {
        // 入力条件をもとに社員一覧を取得
        List<Employee> employeeList = employeeMapper.findByConditions(eId, eName, eKana, dId, dName);

        model.addAttribute("employeeList", employeeList);
        model.addAttribute("loginUserName", session.getAttribute("loginUserName"));
        model.addAttribute("loginUserId", session.getAttribute("loginUserId"));
        return "employee/s-edit";
    }

    // 更新フォームの表示（該当社員をEmployeeFormに変換）
    @GetMapping("/employee/update")
    public String showUpdateForm(@RequestParam("eId") String eId, HttpSession session, Model model) {
        Employee employee = employeeMapper.findById(eId);  // 編集対象の社員データ取得

        model.addAttribute("employeeForm", new EmployeeForm(employee));  // 変換コンストラクタでFormへ
        model.addAttribute("loginUserName", session.getAttribute("loginUserName"));
        model.addAttribute("loginUserId", session.getAttribute("loginUserId"));
        return "employee/s-update";  // 更新画面表示
    }

    // 社員情報の更新処理
    @PostMapping("/employee/update")
    public String updateEmployee(
        @Validated @ModelAttribute("employeeForm") EmployeeForm employeeForm,
        BindingResult bindingResult,
        HttpSession session,
        Model model
    ) {
        String loginUserId = (String) session.getAttribute("loginUserId");

        // 部署IDの存在チェック
        if (employeeForm.getDId() != null && !employeeForm.getDId().isBlank()) {
            if (divisionMapper.findById(employeeForm.getDId()) == null) {
                bindingResult.rejectValue("dId", "notfound", "存在しない部署IDです");
            }
        }

        if (bindingResult.hasErrors()) {
            return "employee/s-update";  // エラー時は更新画面へ戻す
        }

        // フォームの内容をEmployeeへ変換
        Employee emp = new Employee();
        emp.setEId(employeeForm.getEId());
        emp.setEName(employeeForm.getEName());
        emp.setEKana(employeeForm.getEKana());
        emp.setBirthday(employeeForm.getBirthday());
        emp.setDId(employeeForm.getDId());
        emp.setETel(employeeForm.getETel());
        emp.setEEmail(employeeForm.getEEmail());
        emp.setEUpdateName(loginUserId);  // 更新者IDを記録

        employeeMapper.update(emp);  // DB更新

        return "redirect:/employee/edit";  // 編集画面へ戻る
    }

    // 論理削除処理
    @PostMapping("/employee/delete")
    public String deleteEmployee(@RequestParam("eId") String eId) {
        employeeMapper.logicalDelete(eId);
        return "redirect:/employee/edit?deleted=1";
    }
}
