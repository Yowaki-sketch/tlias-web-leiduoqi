package com.atlei.service.impl;

import com.atlei.mapper.EmpMapper;
import com.atlei.pojo.JobOption;
import com.atlei.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {


    @Autowired
    private EmpMapper empMapper;

    @Override
    public JobOption getEmpJobData() {
        // 1. Mapperメソッドを呼び出して役職別の集計データを取得
        List<Map<String, Object>> mapsList = empMapper.countEmpJobData();
        // 2. レスポンス用のデータを組み立てて返却
        List<Object> jobList = mapsList.stream().map(dataMap -> dataMap.get("pos")).toList();
        List<Object> dataList = mapsList.stream().map(dataMap -> dataMap.get("num")).toList();
        JobOption jobOption = new JobOption(jobList, dataList);
        return jobOption;
    }

    @Override
    public List<Map<String, Object>> getEmpGenderData() {
        return empMapper.countEmpGenderData();
    }
}
