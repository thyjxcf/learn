package com.hzgg.chapter4.ReentrantLock;

public class Run2 {

    public static void main(String[] args) {
        MyService2 service2 = new MyService2();
        Thread2 thread2 = new Thread2(service2);
        thread2.start();
    }
}
