package com.yzb.contextSwitch.waitNotify;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestWaitNotify {
    final static Object lock = new Object();

    public static void main(String[] args) {

        new Thread(() -> {
            synchronized (lock) {
                try {
                    // 线程等待
                    log.debug("线程1等待完成");
                    lock.wait();
                    log.debug("线程1被唤醒");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        new Thread(() -> {
            synchronized (lock) {
                // 线程唤醒
                log.debug("线程2唤醒线程1");
                lock.notify();
            }
        }).start();

    }
}
