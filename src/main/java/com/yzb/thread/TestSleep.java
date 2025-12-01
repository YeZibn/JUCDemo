package com.yzb.thread;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestSleep {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // 当线程 sleep 时被中断，会抛出 InterruptedException 异常
                log.debug("interrupted");
                e.printStackTrace();
            }
        });
        t.start();

        // 主线程 sleep 100ms 确保 t 线程能够执行到 sleep 方法
        Thread.sleep(100);
        // 调用 interrupt 方法后，线程的中断状态会被设置为 true
        t.interrupt();
        log.debug("t state: {}", t.getState());
        // 调用 interrupt 方法后，线程的中断状态会被设置为 true，但是如果是sleep、wait、join等方法抛出 InterruptedException 异常，线程的中断状态会被重置为 false
        log.debug("t interrupted: {}", t.isInterrupted());
    }
}
