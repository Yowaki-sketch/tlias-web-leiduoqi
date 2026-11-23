package com.atlei.controller;

import com.atlei.pojo.Emp;
import com.atlei.pojo.JobOption;
import com.atlei.pojo.Result;
import com.atlei.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * データ統計レポートコントローラー
 */
@Slf4j
@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    /**
     * 職種別従業員数の集計
     */
    @GetMapping("/empJobData")
    public Result getEmpJobData() {
        log.info("職種別従業員数を集計します");
        JobOption jobOption = reportService.getEmpJobData();
        return Result.success(jobOption);
    }

    /**
     * 性別従業員数の集計
     */
    @GetMapping("/empGenderData")
    public Result getEmpGenderData() {
        log.info("性別従業員数を集計します");
        List<Map<String, Object>> genderList = reportService.getEmpGenderData();
        return Result.success(genderList);
    }
}
