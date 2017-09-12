package com.hzgg.chapter4.ReentrantLock;

public class Thread6A  extends Thread{
    private MyService6 myService6;

    public Thread6A(MyService6 myService6){
        this.myService6 = myService6;
    }

    @Override
    public void run() {
        myService6.awaitA();
    }
}
