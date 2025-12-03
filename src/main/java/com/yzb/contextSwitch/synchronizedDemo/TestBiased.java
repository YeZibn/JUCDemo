package com.yzb.contextSwitch.synchronizedDemo;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

@Slf4j
public class TestBiased {
    public static void main(String[] args) throws InterruptedException {
        Dog d = new Dog();
        log.debug("{}", ClassLayout.parseInstance(d).toPrintable());

        Thread.sleep(4000);
        log.debug("{}", ClassLayout.parseInstance(d).toPrintable());
    }

}

class Dog{

        }