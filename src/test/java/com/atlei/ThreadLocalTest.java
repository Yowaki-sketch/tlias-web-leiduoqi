package com.atlei;

import org.junit.jupiter.api.Test;

public class ThreadLocalTest {

    private static ThreadLocal<String> local = new ThreadLocal<>();

    @Test
    public void testThreadLocal() {
        local.set("Mian Message");

        new Thread(() -> {
            local.set("Sub Message");
            System.out.println(Thread.currentThread().getName() + "：" + local.get());
        }).start();

        System.out.println(Thread.currentThread().getName() + "：" + local.get());

        local.remove();

        System.out.println(Thread.currentThread().getName() + "：" + local.get());
    }
}
