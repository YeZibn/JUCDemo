package com.yzb.contextSwitch.waitNotify;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestGuardedSuspension {
    public static void main(String[] args) {
        GuardedObject guardedObject = new GuardedObject();
        new Thread(() -> {
            log.debug((String) guardedObject.getResponse(100));
        }).start();

        new Thread(() -> {
            try {
                guardedObject.complete("hello");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

}

@Slf4j
class GuardedObject{
    // 结果
    private Object Response;

    // 等待结果
    public Object getResponse(long timeout) {
        synchronized (this) {
            // 开始时间
            long begin = System.currentTimeMillis();
            while(Response == null) {
                try {
                    log.debug("线程等待");
                    // 超时等待
                    wait(timeout);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                // 超时判断
                if(System.currentTimeMillis() - begin >= timeout) {
                    log.debug("线程等待超时");
                    Response = "超时";
                    break;
                }
            }
            return Response;
        }
    }

    // 完成结果
    public void complete(Object response) throws InterruptedException {
        synchronized (this) {
            log.debug("线程传入结果");
            // 模拟处理时间
            Thread.sleep(4000);
            Response = response;
            notifyAll();
        }
    }
}