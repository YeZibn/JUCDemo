package com.yzb.thread;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestMultiThread {

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            while(true)
                log.info("t1 thread start");
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            while(true)
                log.info("t2 thread start");
        });
        t2.start();
    }
}
