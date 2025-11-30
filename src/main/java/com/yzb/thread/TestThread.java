package com.yzb.thread;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestThread {
    public static void main(String[] args) {

        Thread t = new Thread(){
            @Override
            public void run() {
                log.debug("hello world");
            }
        };
        t.start();
    }
}
