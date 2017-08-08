package com.hzgg.chapter4.ReentrantLock;

public class Thread3 extends Thread {
    private MyService3 myService3;

    public Thread3(MyService3 myService3){
        this.myService3 = myService3;
    }

    @Override
    public void run() {

        myService3.await();
    }
}
