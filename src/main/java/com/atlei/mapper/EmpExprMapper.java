package com.atlei.mapper;


import com.atlei.pojo.Emp;
import com.atlei.pojo.EmpExpr;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 従業員職務経歴マッパー
 */
@Mapper
public interface EmpExprMapper {

    // 従業員職務経歴の一括登録
    void insertBatch(List<EmpExpr> exprList);

    // 従業員IDリストに基づく職務経歴情報の一括削除
    void deleteByEmpIds(List<Integer> empIds);

    // ID指定による従業員基本情報および職務経歴情報の取得
    Emp getById(Integer id);
}
