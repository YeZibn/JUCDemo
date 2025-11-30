package com.yzb.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class TestFutureTask {
    public static void main(String[] args) {
        FutureTask<Integer> task = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 100;
            }
        });
        Thread t = new Thread(task);
        t.start();
    }
}
