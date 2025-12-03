package com.yzb.contextSwitch.waitNotify;

import lombok.extern.slf4j.Slf4j;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

public class TestMail {

    public static void main(String[] args) throws InterruptedException {
        for(int i=0;i<3;i++){
            new People().start();
        }
        Thread.sleep(1000);
        MailBoxes.getIds().forEach(id -> {
            new Postman(id, "邮件" + id).start();
        });


    }
}

@Slf4j
class Postman extends Thread{
    private int id;
    private String mail;

    public Postman(int id, String mail) {
        this.id = id;
        this.mail = mail;
    }

    @Override
    public void run() {
        log.debug("邮递员开始工作");
        log.debug("邮递员开始邮递");
        GuardedObject guardedObject = MailBoxes.getGuardedObject(id);
        guardedObject.complete(mail);
        log.debug("邮递员完成邮递");

    }

}
@Slf4j
class People extends Thread{
    @Override
    public void run() {
        log.debug("人开始工作");
        GuardedObject guardedObject = MailBoxes.createGuardedObject();
        log.debug("收信 id:{}", guardedObject.getId());

        log.debug("收信完成:{}", guardedObject.getResponse(10000));
    }
}

class MailBoxes{
    private static Map<Integer, GuardedObject> mailBoxes = new Hashtable<>();

    private static int id = 1;

    private static synchronized int generateId(){
        return id++;
    }

    public static GuardedObject createGuardedObject(){
        GuardedObject guardedObject = new GuardedObject(generateId());
        mailBoxes.put(guardedObject.getId(), guardedObject);
        return guardedObject;
    }

    public static Set<Integer> getIds(){
        return mailBoxes.keySet();
    }

    public static GuardedObject getGuardedObject(int id){
        return mailBoxes.remove(id);
    }
}

@Slf4j
class GuardedObject{
    private int id;

    public GuardedObject(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    private Object response;

    /**
     * 改进版超时等待方法
     * 1. 正确处理虚假唤醒
     * 2. 精确计算剩余等待时间
     * 3. 保持中断状态
     */
    public Object getResponse(long timeoutMillis) {
        synchronized (this) {
            long remainingTime = timeoutMillis;
            long start = System.currentTimeMillis();
            boolean interrupted = false;

            while (response == null && remainingTime > 0) {
                try {
                    log.debug("线程等待，剩余时间: {}ms", remainingTime);
                    wait(remainingTime);
                } catch (InterruptedException e) {
                    // 记录中断状态但不立即抛出
                    interrupted = true;
                    log.debug("等待被中断");
                    // 重新计算剩余等待时间
                    remainingTime = timeoutMillis - (System.currentTimeMillis() - start);
                }

                // 重新计算剩余等待时间
                remainingTime = timeoutMillis - (System.currentTimeMillis() - start);
            }

            // 恢复中断状态
            if (interrupted) {
                Thread.currentThread().interrupt();
            }

            // 超时处理
            if (response == null) {
                log.debug("线程等待超时");
                response = "超时"; // 可以考虑返回null或抛出异常
            }

            return response;
        }
    }

    /**
     * 改进版结果设置方法
     * 1. 不在锁内进行耗时操作
     * 2. 更优雅地通知等待线程
     */
    public void complete(String mail) {
        // 先处理耗时操作，避免长时间持有锁
        try {
            log.debug("开始送信");
            Thread.sleep(4000); // 模拟处理时间
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("处理过程被中断", e);
            return;
        }

        // 锁内只进行状态更新和通知
        synchronized (this) {
            log.debug("信件放入邮箱: {}", mail);
            response = mail;
            notifyAll(); // 通知所有等待线程
        }
    }
}
