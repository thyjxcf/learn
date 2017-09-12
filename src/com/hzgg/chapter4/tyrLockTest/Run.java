package com.hzgg.chapter4.tyrLockTest;

public class Run {
    public static void main(String[] args) {
        final MyService myService = new MyService();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                myService.waitMethod();
            }
        };
        Thread threada = new Thread(runnable);
        threada.setName("A");
        threada.start();
        Thread threadb = new Thread(runnable);
        threadb.setName("B");
        threadb.start();
    }

}
