package com.atlei.service;

import com.atlei.pojo.Emp;
import com.atlei.pojo.EmpQueryParam;
import com.atlei.pojo.LoginInfo;
import com.atlei.pojo.PageResult;

import java.util.List;

public interface EmpService {
    /**
     * 従業員情報のページネーション検索
     *
     * @param empQueryParam.page     ページ番号
     * @param empQueryParam.pageSize 1ページあたりの表示件数
     * @param empQueryParam.name     氏名
     * @param empQueryParam.gender   性別
     * @param empQueryParam.begin    入社日の開始日
     * @param empQueryParam.end      入社日の終了日
     */
    PageResult<Emp> page(EmpQueryParam empQueryParam);

    // 従業員の新規登録
    void save(Emp emp) throws Exception;

    // 従業員の一括削除
    void delete(List<Integer> ids);

    // ID指定による従業員詳細情報の取得
    Emp getInfoById(Integer id);

    // 従業員情報の更新
    void update(Emp emp);

    // 従業員ログイン認証
    LoginInfo login(Emp emp);
}