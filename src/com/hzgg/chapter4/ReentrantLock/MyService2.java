package com.hzgg.chapter4.ReentrantLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyService2 {
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void await() throws InterruptedException {
        try{
            lock.lock();
            System.out.println("A");
            condition.await();
            System.out.println("B");
        }finally {
            lock.unlock();
        }

    }


}
