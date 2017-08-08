package com.hzgg.chapter4.ReentrantLock;

public class Run {

    public static void main(String[] args) {
        MyService service = new MyService();
        MyThread threada = new MyThread(service);
        MyThread threadb = new MyThread(service);
        MyThread threadc = new MyThread(service);
        MyThread threadd = new MyThread(service);
        threada.start();
        threadb.start();
        threadc.start();
        threadd.start();
    }
}
