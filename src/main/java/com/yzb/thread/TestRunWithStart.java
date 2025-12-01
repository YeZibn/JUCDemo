package com.yzb.thread;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestRunWithStart {
    public static void main(String[] args){

        // 调用run方法不会启动新线程，而是在当前线程中执行run方法
        Thread t = new Thread(){
            @Override
            public void run(){
                log.debug("running......");
            }
        };
        t.run();
        t.start();
    }
}
