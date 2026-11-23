package com.atlei.controller;

import com.atlei.anno.Log;
import com.atlei.pojo.Dept;
import com.atlei.pojo.Result;
import com.atlei.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部署管理コントローラー
 */
@Slf4j
@RequestMapping("/depts")
@RestController
public class DeptController {

    @Autowired
    private DeptService deptService;

    /**
     * 全部署一覧の取得
     */
    @GetMapping
    public Result list() {
        log.info("全部署一覧を取得します");
        List<Dept> deptList = deptService.findAll();
        return Result.success(deptList);
    }

    /**
     * ID指定による部署の削除
     */
    @Log
    @DeleteMapping
    public Result deleteDept(Integer id) {
        log.info("部署を削除します: id={}", id);
        deptService.deleteById(id);
        return Result.success();
    }

    /**
     * 部署の新規登録
     */
    @Log
    @PostMapping
    public Result addDept(@RequestBody Dept dept) {
        log.info("部署を新規登録します: {}", dept);
        deptService.add(dept);
        return Result.success();
    }

    /**
     * ID指定による部署詳細の取得
     */
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id) {
        log.info("部署詳細を取得します: id={}", id);
        Dept dept = deptService.getById(id);
        return Result.success(dept);
    }

    /**
     * 部署情報の更新
     */
    @Log
    @PutMapping
    public Result updateDept(@RequestBody Dept dept) {
        log.info("部署情報を更新します: {}", dept);
        deptService.update(dept);
        return Result.success();
    }
}