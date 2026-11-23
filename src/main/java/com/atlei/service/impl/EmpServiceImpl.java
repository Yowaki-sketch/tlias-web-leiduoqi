package com.atlei.service.impl;

import com.atlei.mapper.EmpExprMapper;
import com.atlei.mapper.EmpMapper;
import com.atlei.pojo.*;
import com.atlei.service.EmpLogService;
import com.atlei.service.EmpService;
import com.atlei.utils.JwtUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private EmpExprMapper empExprMapper;

    @Autowired
    private EmpLogService empLogService;

    @Override
    public PageResult<Emp> page(EmpQueryParam empQueryParam) {
        // 1. ページネーションパラメータの設定
        PageHelper.startPage(empQueryParam.getPage(), empQueryParam.getPageSize());
        // 2. ページネーション検索の実行
        List<Emp> empList = empMapper.list(empQueryParam);
        // 3. 検索結果の解析およびカプセル化
        Page<Emp> p = (Page<Emp>) empList;
        return new PageResult<Emp>(p.getTotal(), p.getResult());
    }

    /**
     * 従業員情報および職務経歴の一括登録（トランザクション制御：すべての例外でロールバック）
     */
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void save(Emp emp) throws Exception {
        try {
            // 1. 従業員基本情報の登録
            emp.setCreateTime(LocalDateTime.now());
            emp.setUpdateTime(LocalDateTime.now());
            empMapper.insert(emp);

            // 2. 従業員職務経歴情報の登録
            List<EmpExpr> exprList = emp.getExprList();
            if (!CollectionUtils.isEmpty(exprList)) {
                // リストを走査し、生成された従業員ID(empId)を設定
                exprList.forEach(e -> {
                    e.setEmpId(emp.getId());
                });
                empExprMapper.insertBatch(exprList);
            }
        } finally {
            // 3. 操作ログの記録（別トランザクションで実行）
            EmpLog empLog = new EmpLog(null, LocalDateTime.now(), "新規従業員登録: " + emp);
            empLogService.insertLog(empLog);
        }
    }

    @Transactional(rollbackFor = {Exception.class}) // トランザクション制御（例外発生時にロールバック）
    @Override
    public void delete(List<Integer> ids) {
        // 1. 従業員基本情報の削除
        empMapper.deleteByIds(ids);
        // 2. 従業員職務経歴情報の削除
        empExprMapper.deleteByEmpIds(ids);
    }

    @Override
    public Emp getInfoById(Integer id) {
        return empExprMapper.getById(id);
    }

    @Transactional(rollbackFor = Exception.class) // トランザクション制御の適用
    @Override
    public void update(Emp emp) {
        // 1. ID指定による従業員基本情報の更新
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.updateById(emp);
        // 2. ID指定による従業員職務経歴情報の更新
        // 2.1 既存の職務経歴データを削除
        empExprMapper.deleteByEmpIds(Arrays.asList(emp.getId()));
        // 2.2 新しい職務経歴データを一括登録
        List<EmpExpr> exprList = emp.getExprList();
        if (!CollectionUtils.isEmpty(exprList)) {
            exprList.forEach(empExpr -> empExpr.setEmpId(emp.getId()));
            empExprMapper.insertBatch(exprList);
        }
    }

    @Override
    public LoginInfo login(Emp emp) {
        // 1. Mapperを呼び出し、ユーザー名とパスワードでアカウント情報を検索
        Emp emp1 = empMapper.selectByUsernameAndPassword(emp);
        // 2.0 アカウントが存在する場合、JWTトークンを生成してレスポンス情報を返却
        if (emp1 != null) {
            log.info("ログイン成功: {}", emp1);
            // JWTトークンの生成
            HashMap<String, Object> claims = new HashMap<>();
            claims.put("id", emp1.getId());
            claims.put("username", emp1.getUsername());
            String jwt = JwtUtils.generateToken(claims);
            // Controllerへ返却
            return new LoginInfo(emp1.getId(), emp1.getUsername(), emp1.getName(), jwt);
        } else {
            // 2.1 アカウントが存在しない場合はnullを返却（Controller側でエラーレスポンスを生成）
            return null;
        }
    }
}