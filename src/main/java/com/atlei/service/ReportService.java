package
com.atlei.service;

import
        com.atlei.pojo.JobOption;

import
        java.util.List;
import
        java.util.Map;

public interface ReportService
{
    // 役職別の従業員数集計
    JobOption getEmpJobData();

    // 性別ごとの従業員数集計
    List<Map<String, Object>> getEmpGenderData();
}
