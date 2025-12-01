package com.yzb.thread;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestJoin {
        static int i = 0;
        public static void main(String[] args) throws InterruptedException {
            Thread t1 = new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    i = 1;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            });
            t1.start();
            t1.join();
            log.debug("i = {}", i);
        }
}
