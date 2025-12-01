package com.yzb.thread;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestYield {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                // 调用 yield 方法后，线程会让出 CPU 时间片，但是具体是否让出，取决于操作系统的调度策略
                Thread.yield();
                log.debug("t1: {}", i);
            }
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                log.debug("t2: {}", i);
            }
        });
        t2.start();
    }
}
