//package com.atlei.filter;
//
//import jakarta.servlet.*;
//import jakarta.servlet.annotation.WebFilter;
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.IOException;
//
//@Slf4j
////@WebFilter(urlPatterns = "/login")
//public class EbcFilter implements Filter {
//
//    //过滤器和新方法，每次拦截都会执行
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        log.info("ABD拦截到了请求");
//        // 放行
//        log.info("ABC放行前" +
//                "的逻辑执行了");
//        chain.doFilter(request, response);
//        // 放行后的逻辑
//        log.info("ABC放行后的逻辑运行了");
//    }
//
////    // 初始化方法 web服务器启动时执行一次
////    @Override
////    public void init(FilterConfig filterConfig) throws ServletException {
////        log.info("init初始化...");
////    }
////
////    // 销毁方法 web服务器关闭时执行一次
////    @Override
////    public void destroy() {
////        log.info("destroy销毁中...");
////    }
//}
