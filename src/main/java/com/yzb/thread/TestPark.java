package com.yzb.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

@Slf4j
public class TestPark {
    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            log.debug("park...");
            // 阻塞当前线程
            LockSupport.park();
            log.debug("unpark...");
            log.debug("打断状态: {}", Thread.currentThread().isInterrupted());
        });

        t.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.interrupt();
    }
}
