package com.atlei;

import com.atlei.pojo.Result;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class TliasWebManagementApplicationTests {

    /**
     * 默认IOC容器中的Bean都是单例的
     * 且Bean的创建时机一般都是在项目启动时创建的，创建完毕后就会存入IOC容器
     */
    @Autowired
    private Gson gson;

    //我自己用@Bean声明的第三方的豆子，并自动注入使用，
    @Test
    public void testGson() {
        System.out.println(gson.toJson(Result.success("我自己引入的Gson，并且是我自己写的@Bean注解声明Bean")));
    }

    @Autowired
    private ApplicationContext applicationContext; // todo:该类为IOC容器类，该变量为IOC容器的对象

    @Test//用IOC容器对象获取里面的Bean对象，并进行100次打印
    public void testScope() {
        for (int i = 0; i < 1000; i++) {
            Object deptController = applicationContext.getBean("deptController");
            System.out.println(deptController);
        }

    }


}
