package com.hzgg.chapter4.ReentrantLock;

public class Run5 {

    public static void main(String[] args) {

        MyService5 service5 = new MyService5();

        Thread5A thread5A = new Thread5A(service5);
        thread5A.setName("A");
        thread5A.start();

        Thread5B thread5B = new Thread5B(service5);
        thread5B.setName("B");
        thread5B.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        service5.singleAll();
    }
}
