package com.yzb.thread;

public class TestRunnable {

    public static void main(String[] args) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello world");
            }
        });
        t.start();
    }

}
