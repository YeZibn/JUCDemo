package com.yzb.thread;

public class TestDaemon {
    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("子线程执行中");
            }
        });
        // 设置为守护线程
        t.setDaemon(true);
        t.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程执行完毕");
    }
}
