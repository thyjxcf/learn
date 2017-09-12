package com.hzgg.chapter4.ReentrantLock;

public class Run6 {
    public static void main(String[] args) {
        MyService6 service6 = new MyService6();
        Thread6A thread6A = new Thread6A(service6);
        thread6A.setName("A");
        thread6A.start();
        Thread6B thread6B = new Thread6B(service6);
        thread6B.setName("B");
        thread6B.start();
        try {
            Thread.sleep(3000);
            service6.singleAll_A();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
