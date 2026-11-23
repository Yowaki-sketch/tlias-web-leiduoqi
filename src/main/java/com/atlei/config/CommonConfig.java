package com.atlei.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // 1. 声明为配置类，内部方法返回的对象将注册进 Spring 容器
public class CommonConfig {

    /**
     * 声明一个第三方库的 Gson 实例
     * 方法名（gson）默认就是注册进容器的 Bean 名称
     * 也可以显式指定：@Bean("gouSen")
     */
//    @Bean
//    @ConditionalOnMissingBean//Conditional系的注解，todo:直译：有条件的、附条件的，conditional 作形容词表示某事只有在满足特定条件时才成立、发生或有效。
//    public ObjectMapper objectMapper() {
//        ObjectMapper mapper = new ObjectMapper();
//        // 可以在这里书写复杂的第三方对象定制化配置，比如忽略空字段、配置日期序列化格式等
//        return mapper;
//    }

    @Bean("gouSen")
    public Gson gson() {
        Gson gson = new Gson();
        return gson;
    }
}