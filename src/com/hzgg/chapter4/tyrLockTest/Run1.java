package com.hzgg.chapter4.tyrLockTest;

public class Run1 {

    public static void main(String[] args) {
        final MyService1 myService1 = new MyService1();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(" thread==" + Thread.currentThread().getName() + " tims is ==" + System.currentTimeMillis());
                myService1.waitMethod();
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
