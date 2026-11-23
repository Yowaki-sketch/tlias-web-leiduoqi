package com.atlei.controller;

import com.atlei.pojo.Emp;
import com.atlei.pojo.LoginInfo;
import com.atlei.pojo.Result;
import com.atlei.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * ログイン認証コントローラー
 */
@Slf4j
@RestController
public class LoginController {

    @Autowired
    private EmpService empService;

    /**
     * ログイン認証およびJWTトークンの発行
     */
    @PostMapping("/login")
    public Result login(@RequestBody Emp emp) {
        log.info("ログイン認証リクエスト: username={}", emp.getUsername());
        LoginInfo info = empService.login(emp);
        if (info != null) {
            return Result.success(info);
        } else {
            return Result.error("ユーザー名またはパスワードが間違っています");
        }
    }
}