package com.atlei.controller;

import com.atlei.anno.Log;
import com.atlei.pojo.Emp;
import com.atlei.pojo.EmpQueryParam;
import com.atlei.pojo.PageResult;
import com.atlei.pojo.Result;
import com.atlei.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 従業員管理コントローラー
 */
@Slf4j
@RestController
@RequestMapping("/emps")
public class EmpController {

    @Autowired
    private EmpService empService;

    /**
     * 従業員一覧のページネーション・条件検索
     */
    @GetMapping
    public Result page(EmpQueryParam empQueryParam) {
        log.info("従業員一覧を検索します: {}", empQueryParam);
        PageResult<Emp> pageResult = empService.page(empQueryParam);
        return Result.success(pageResult);
    }

    /**
     * 従業員の新規登録
     */
    @Log
    @PostMapping
    public Result save(@RequestBody Emp emp) throws Exception {
        log.info("従業員を新規登録します: {}", emp);
        empService.save(emp);
        return Result.success();
    }

    /**
     * ID指定による従業員の一括削除
     */
    @Log
    @DeleteMapping
    public Result delete(@RequestParam List<Integer> ids) {
        log.info("従業員を一括削除します: ids={}", ids);
        empService.delete(ids);
        return Result.success();
    }

    /**
     * ID指定による従業員詳細情報の取得
     */
    @GetMapping("/{id}")
    public Result getInfoById(@PathVariable Integer id) {
        log.info("従業員詳細情報を取得します: id={}", id);
        Emp emp = empService.getInfoById(id);
        return Result.success(emp);
    }

    /**
     * 従業員情報の更新
     */
    @Log
    @PutMapping
    public Result update(@RequestBody Emp emp) {
        log.info("従業員情報を更新します: {}", emp);
        empService.update(emp);
        return Result.success();
    }
}