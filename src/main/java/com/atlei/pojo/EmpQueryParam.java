package com.atlei.pojo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class EmpQueryParam {
    private Integer page;  //页码
    private Integer pageSize; //每页展示的数据记录数
    private String name; //姓名
    private Integer gender; //性别
    @DateTimeFormat(pattern = "yyyy-MM-dd") //解析时间日期的格式的注解
    private LocalDate begin; //入职查询开始日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end; //入职查询结束日期

}
