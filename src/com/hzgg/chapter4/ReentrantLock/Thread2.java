package com.hzgg.chapter4.ReentrantLock;

public class Thread2 extends Thread {
    private MyService2 myService2;

    public Thread2(MyService2 service2){
        this.myService2 = service2;
    }

    @Override
    public void run() {

        try {
            myService2.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
