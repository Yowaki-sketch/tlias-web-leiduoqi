package com.atlei.service.impl;

import com.atlei.mapper.DeptMapper;
import com.atlei.pojo.Dept;
import com.atlei.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {


    @Autowired
    private DeptMapper deptMapper;

    @Override
    public List<Dept> findAll() {
        return deptMapper.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        deptMapper.deleteById(id);
    }

    @Override
    public void add(Dept dept) {
        // 1. 基本属性の補完（作成日時・更新日時）
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        // 2. Mapperメソッドを呼び出してデータを登録
        deptMapper.insert(dept);
    }

    @Override
    public Dept getById(Integer id) {
        return deptMapper.getById(id);
    }

    @Override
    public void update(Dept dept) {
        // 1. 基本属性の補完（更新日時）
        dept.setUpdateTime(LocalDateTime.now());
        // 2. Mapperを呼び出してデータベースを更新
        deptMapper.update(dept);
    }
}