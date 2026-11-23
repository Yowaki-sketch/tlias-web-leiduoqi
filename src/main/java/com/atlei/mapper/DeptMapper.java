package com.atlei.mapper;

import com.atlei.pojo.Dept;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DeptMapper {

    /**
     * 全部署データの取得（更新日時の降順）
     * ※ スネークケース(snake_case)とキャメルケース(Camel-Case)の自動マッピングを有効化済み
     */
    @Select("select id, name, create_time, update_time from dept order by update_time desc")
    List<Dept> findAll();

    // ID指定による部署データの削除
    @Delete("delete from dept where id = #{id}")
    void deleteById(Integer id);

    // 部署の新規登録
    @Insert("insert into dept(name, create_time, update_time) value (#{name},#{createTime},#{updateTime})")
    void insert(Dept dept);

    // ID指定による部署情報の取得
    @Select("select id, name, create_time, update_time from dept where id = #{id}")
    Dept getById(Integer id);

    // 部署情報の更新
    @Update("update dept set name = #{name}, update_time = #{updateTime} where id = #{id}")
    void update(Dept dept);
}