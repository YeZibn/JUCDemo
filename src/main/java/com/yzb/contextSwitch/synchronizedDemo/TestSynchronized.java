package com.yzb.contextSwitch.synchronizedDemo;

public class TestSynchronized {
    static final Object lock = new Object();
    static int counter = 0;

    public static void main(){
        // 测试 synchronized 关键字是否可以保证线程安全
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                for (int i = 0; i < 10000; i++) {
                    counter++;
                }
            }
        });
        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                for (int i = 0; i < 10000; i++) {
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
        System.out.println(counter);
    }
}
