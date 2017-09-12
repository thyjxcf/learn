package com.hzgg.chapter4.ReentrantLock;

public class Thread5B extends Thread {
    private MyService5 myService5;

    public Thread5B(MyService5 myService5){
        this.myService5 = myService5;
    }

    @Override
    public void run() {
        myService5.awaitA();
    }
}
