package com.hzgg.chapter4.ReentrantLock;

public class ThreadAA extends Thread {

    private  MyService1 service1;

    public ThreadAA(MyService1 service1){
        this.service1 = service1;
    }

    @Override
    public void run() {
        service1.methodA();
    }

}
