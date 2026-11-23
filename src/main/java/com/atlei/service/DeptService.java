package com.atlei.service;

import com.atlei.pojo.Dept;

import java.util.List;

public interface DeptService {

    // 全部署データの取得
    List<Dept> findAll();

    // ID指定による部署データの削除
    void deleteById(Integer id);

    // 部署の新規登録
    void add(Dept dept);

    // ID指定による部署情報の取得
    Dept getById(Integer id);

    // 部署情報の更新
    void update(Dept dept);
}