package com.atlei.mapper;


import com.atlei.pojo.Emp;
import com.atlei.pojo.EmpQueryParam;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/*
 * 操作员工信息的接口
 * */
@Mapper
public interface EmpMapper {

    /**
     * 原始分页查询的实现
     * <p>
     * 查询总记录数
     *
     * @Select("select count(*) from emp")
     * public Long count();
     * <p>
     * 分页查询
     * @Select("select e.*, d.name as deptName from emp as e left join dept as d on e.dept_id = d.id " +
     * "order by e.update_time desc " +
     * "limit #{start},#{pageSize}")
     * public List<Emp> list(Integer start, Integer pageSize);
     */

    // @Select("select e.*, d.name as deptName from emp as e left join dept as d on e.dept_id = d.id " +
    // "order by e.update_time desc")
    //public List<Emp> list(String name, Integer gender, LocalDate begin, LocalDate end);

    // 条件指定による従業員情報の検索
    List<Emp> list(EmpQueryParam empQueryParam);

    // 従業員基本情報の新規登録（自動採番された主キーIDを返却）
    @Options(useGeneratedKeys = true, keyProperty = "id")// todo:Mybatis提供的功能，主键返回
    @Insert("insert into emp (username,name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time)" +
            " values (#{username},#{name},#{gender},#{phone},#{job},#{salary},#{image},#{entryDate},#{deptId},#{createTime},#{updateTime})")
    void insert(Emp emp);

    // IDリストに基づく従業員基本情報の一括削除
    void deleteByIds(List<Integer> ids);

    // ID指定による従業員基本情報の更新
    void updateById(Emp emp);

    // 役職別の従業員数集計
    List<Map<String, Object>> countEmpJobData();

    // 性別ごとの従業員数集計
    List<Map<String, Object>> countEmpGenderData();

    // ユーザー名とパスワードによる従業員認証情報の取得
    @Select("select id, username, name from emp where username = #{username} and password = #{password}")
    Emp selectByUsernameAndPassword(Emp emp);
}