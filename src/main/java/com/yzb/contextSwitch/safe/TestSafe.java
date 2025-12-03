package com.yzb.contextSwitch.safe;

import java.util.ArrayList;

public class TestSafe {
    public static void main(String[] args){
        ThreadSafe threadSafe = new ThreadSafe();
        Thread thread1 = new Thread(() -> {
            threadSafe.method1();
        });
        thread1.start();
        Thread thread2 = new Thread(() -> {
            threadSafe.method1();
        });
        thread2.start();
    }
}

class ThreadSafe{
    public final void method1(){
        ArrayList<Integer> list = new ArrayList<>();
        for(int i = 0; i < 10000; i++){
            method2(list);
            method3(list);
        }
    }

    public void method2(ArrayList<Integer> list){
        list.add(1);
    }

    public void method3(ArrayList<Integer> list){
        new Thread(() -> {
            list.remove(0);
        }).start();
    }
}
