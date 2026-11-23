package com.atlei;

import com.atlei.utils.MyImportSelector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

//@Import(MyImportSelector.class)用于引入外源Bean的注解
//@ComponentScan(basePackages = "com.atlei")// Spring自己的组件扫描注解，一旦显示声明，以前的默认扫描范围则不再生效
@ServletComponentScan// todo:该注解意味着开启Spring对Servlet组件的扫描
@SpringBootApplication
public class TliasWebManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(TliasWebManagementApplication.class, args);
    }
}
