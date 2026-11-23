package com.atlei.service;

import com.atlei.pojo.EmpLog;

public interface EmpLogService {

    // 操作ログの登録
    public void insertLog(EmpLog empLog);

}