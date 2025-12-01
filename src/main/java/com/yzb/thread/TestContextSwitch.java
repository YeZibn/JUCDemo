package com.yzb.thread;

public class TestContextSwitch {
    static int counter = 0;
    static Object lock = new Object();

    public static void main(String[] args) {
        // 测试上下文切换是否会导致 counter 最终结果为 0
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                synchronized (lock) {
                    counter++;
                }
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                synchronized (lock) {
                    counter--;
                }
            }
        });
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("counter = " + counter);
    }
}
