package com.hzgg.chapter4.ReentrantLock;

public class Run3 {

    public static void main(String[] args) {
        MyService3 service3 = new MyService3();

        Thread3 thread3 = new Thread3(service3);
        thread3.start();
        try {
            Thread.sleep(1000);
            service3.single();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
