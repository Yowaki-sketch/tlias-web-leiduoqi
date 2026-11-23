package com.atlei.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobOption {
    private List jobList;//职位名列表
    private List dataList;//职员人数列表
}
