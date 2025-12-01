package com.yzb.thread;

import lombok.extern.slf4j.Slf4j;

class Test{
    public static void main(String[] args) {
        TwoPhaseTermination twoPhaseTermination = new TwoPhaseTermination();
        twoPhaseTermination.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        twoPhaseTermination.stop();
    }
}

@Slf4j
public class TwoPhaseTermination {
    private Thread monitorThread;

    // 启动监控线程
    public void start(){
        monitorThread = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    log.debug("monitor thread terminated");
                    break;
                }
                else {
                    try {
                        Thread.sleep(1000);
                        log.debug("执行监控记录");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        // 重置中断状态
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });
        monitorThread.start();
    }

    // 终止监控线程
    public void stop(){
        monitorThread.interrupt();
    }
}
