package com.atlei;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; // 正确的包路径


public class LogTest {
    private static final Logger log = LoggerFactory.getLogger(LogTest.class);

    @Test
    public void testLog() {
        //System.out.println(LocalDateTime.now() + " : 开始计算...");
        log.debug("开始计算...");

        int sum = 0;
        int[] nums = {1, 5, 3, 2, 1, 4, 5, 4, 6, 7, 4, 34, 2, 23};
        for (int num : nums) {
            sum += num;
        }

        //System.out.println("计算结果为: " + sum);
        //System.out.println(LocalDateTime.now() + "结束计算...");
        log.info("计算结果为：" + sum);
        log.debug("结束计算...");
        log.trace("无所吊慰的trace级别...");
        log.warn("比较重要的warn警告级别...");
        log.error("最严重的error，不知会不会爆红...");
    }

}
